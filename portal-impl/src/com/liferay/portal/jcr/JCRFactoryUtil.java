/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.jcr;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * <a href="JCRFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 */
public class JCRFactoryUtil {

	public static JCRFactory getJCRFactory() {
		if (_jcrFactory == null) {
			_jcrFactory = (JCRFactory)PortalBeanLocatorUtil.locate(
				_JCR_FACTORY);
		}

		return _jcrFactory;
	}

	public static Session createSession(String workspaceName)
		throws RepositoryException {

		if (workspaceName == null) {
			workspaceName = JCRFactory.WORKSPACE_NAME;
		}

		return getJCRFactory().createSession(workspaceName);
	}

	public static Session createSession() throws RepositoryException {
		return createSession(null);
	}

	public static void initialize() throws RepositoryException {
		getJCRFactory().initialize();
	}

	public static void prepare() throws RepositoryException {
		getJCRFactory().prepare();
	}

	public static void shutdown() throws RepositoryException {
		getJCRFactory().shutdown();
	}

	private static final String _JCR_FACTORY = JCRFactory.class.getName();

	private static JCRFactory _jcrFactory;

}