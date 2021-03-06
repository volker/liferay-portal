/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteConfig;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MergeCentralSubrepositoryUtil {

	public static void createSubrepositoryMergePullRequests(
			String centralWorkingDirectory, String centralUpstreamBranchName,
			String receiverUserName, String topLevelBranchName)
		throws GitAPIException, IOException {

		GitWorkingDirectory centralGitWorkingDirectory =
			new GitWorkingDirectory(
				centralUpstreamBranchName, centralWorkingDirectory);

		File modulesDir = new File(
			centralGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (modulesDir.exists()) {
			List<File> gitrepoFiles = JenkinsResultsParserUtil.findFiles(
				modulesDir, ".gitrepo");

			for (File gitrepoFile : gitrepoFiles) {
				CentralSubrepository centralSubrepository =
					new CentralSubrepository(
						gitrepoFile, centralUpstreamBranchName);

				if (centralSubrepository.isCentralPullRequestCandidate()) {
					String mergeBranchName = _getMergeBranchName(
						centralSubrepository.getSubrepositoryName(),
						centralSubrepository.getSubrepositoryUpstreamCommit());
					RemoteConfig upstreamRemoteConfig =
						centralGitWorkingDirectory.getRemoteConfig("upstream");

					if (!centralGitWorkingDirectory.branchExists(
							mergeBranchName, upstreamRemoteConfig)) {

						_createMergeBranch(
							centralGitWorkingDirectory, centralSubrepository,
							topLevelBranchName);

						_commitCiMergeFile(
							centralGitWorkingDirectory, centralSubrepository,
							gitrepoFile);

						_pushMergeBranchToRemote(
							centralGitWorkingDirectory, centralSubrepository,
							receiverUserName);
					}

					_createMergePullRequest(
						centralGitWorkingDirectory, centralSubrepository,
						receiverUserName);
				}
			}
		}
	}

	private static void _commitCiMergeFile(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, File gitrepoFile)
		throws GitAPIException, IOException {

		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String ciMergeFilePath = _getCiMergeFilePath(
			centralGitWorkingDirectory, gitrepoFile);

		JenkinsResultsParserUtil.write(
			new File(
				centralGitWorkingDirectory.getWorkingDirectory(),
				ciMergeFilePath),
			subrepositoryUpstreamCommit);

		centralGitWorkingDirectory.stageFileInCurrentBranch(ciMergeFilePath);

		centralGitWorkingDirectory.commitStagedFilesToCurrentBranch(
			"Create " + ciMergeFilePath + ".");
	}

	private static void _createMergeBranch(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository,
			String topLevelBranchName)
		throws GitAPIException, IOException {

		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);

		centralGitWorkingDirectory.reset("head", ResetType.HARD);

		centralGitWorkingDirectory.checkoutBranch(topLevelBranchName);

		centralGitWorkingDirectory.deleteLocalBranch(mergeBranchName);

		centralGitWorkingDirectory.createLocalBranch(mergeBranchName);

		centralGitWorkingDirectory.checkoutBranch(mergeBranchName);
	}

	private static void _createMergePullRequest(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String receiverUserName)
		throws IOException {

		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", receiverUserName, "/",
			subrepositoryName, "/statuses/", subrepositoryUpstreamCommit);

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("context", "liferay/central-pull-request");
		requestJSONObject.put("description", "Tests are queued on Jenkins.");
		requestJSONObject.put("state", "pending");

		String body = JenkinsResultsParserUtil.combine(
			"Merging the following commit: [", subrepositoryUpstreamCommit,
			"](https://github.com/", receiverUserName, "/", subrepositoryName,
			"/commit/", subrepositoryUpstreamCommit, ")");
		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);
		String title = subrepositoryName + " - Central Merge Pull Request";

		String pullRequestURL = centralGitWorkingDirectory.createPullRequest(
			body, mergeBranchName, receiverUserName, title);

		requestJSONObject.put("target_url", pullRequestURL);

		JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());
	}

	private static String _getCiMergeFilePath(
			GitWorkingDirectory centralGitWorkingDirectory, File gitrepoFile)
		throws IOException {

		File centralWorkingDirectory =
			centralGitWorkingDirectory.getWorkingDirectory();

		String ciMergeFilePath = gitrepoFile.getCanonicalPath();

		ciMergeFilePath = ciMergeFilePath.replace(".gitrepo", "ci-merge");

		return ciMergeFilePath.replace(
			centralWorkingDirectory.getCanonicalPath() + File.separator, "");
	}

	private static String _getMergeBranchName(
		String subrepositoryName, String subrepositoryUpstreamCommit) {

		return JenkinsResultsParserUtil.combine(
			"ci-merge-", subrepositoryName, "-", subrepositoryUpstreamCommit);
	}

	private static void _pushMergeBranchToRemote(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String receiverUserName)
		throws GitAPIException, IOException {

		String centralRepositoryName =
			centralGitWorkingDirectory.getRepositoryName();
		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);

		String originRemoteURL = JenkinsResultsParserUtil.combine(
			"git@github.com:", receiverUserName, "/", centralRepositoryName,
			".git");

		centralGitWorkingDirectory.pushToRemote(
			false, mergeBranchName, originRemoteURL);
	}

}