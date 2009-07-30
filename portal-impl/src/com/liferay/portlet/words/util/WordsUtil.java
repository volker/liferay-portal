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

package com.liferay.portlet.words.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portlet.words.ScramblerException;
import com.liferay.util.jazzy.BasicSpellCheckListener;
import com.liferay.util.jazzy.InvalidWord;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.DefaultWordFinder;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="WordsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WordsUtil {

	public static List<InvalidWord> checkSpelling(String text) {
		return _instance._checkSpelling(text);
	}

	public static List<String> getDictionaryList() {
		return _instance._getDictionaryList();
	}

	public static Set<String> getDictionarySet() {
		return _instance._getDictionarySet();
	}

	public static String getRandomWord() {
		return _instance._getRandomWord();
	}

	public static boolean isDictionaryWord(String word) {
		return _instance._isDictionaryWord(word);
	}

	public static String[] scramble(String word) throws ScramblerException {
		Scrambler scrambler = new Scrambler(word);

		return scrambler.scramble();
	}

	public static String[] unscramble(String word) throws ScramblerException {
		return _instance._unscramble(word);
	}

	private WordsUtil() {
		_dictionaryList = ListUtil.fromArray(StringUtil.split(
			ContentUtil.get("com/liferay/portlet/words/dependencies/words.txt"),
			"\n"));

		_dictionaryList = new UnmodifiableList<String>(_dictionaryList);

		_dictionarySet = new HashSet<String>(_dictionaryList.size());

		_dictionarySet.addAll(_dictionaryList);

		_dictionarySet = Collections.unmodifiableSet(_dictionarySet);

		try {
			_spellDictionary = new SpellDictionaryHashMap();

			String[] dics = new String[] {
				"center.dic", "centre.dic", "color.dic", "colour.dic",
				"eng_com.dic", "english.0", "english.1", "ise.dic", "ize.dic",
				"labeled.dic", "labelled.dic", "yse.dic", "yze.dic"
			};

			for (int i = 0; i < dics.length; i++) {
				_spellDictionary.addDictionary(new StringReader(
					ContentUtil.get(
						"com/liferay/portlet/words/dependencies/" + dics[i])));
			}
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}
	}

	private List<InvalidWord> _checkSpelling(String text) {
		SpellChecker checker = new SpellChecker(_spellDictionary);

		BasicSpellCheckListener listener = new BasicSpellCheckListener(text);

		checker.addSpellCheckListener(listener);

		checker.checkSpelling(
			new StringWordTokenizer(new DefaultWordFinder(text)));

		return listener.getInvalidWords();
	}

	private List<String> _getDictionaryList() {
		return _dictionaryList;
	}

	private Set<String> _getDictionarySet() {
		return _dictionarySet;
	}

	private String _getRandomWord() {
		int pos = Randomizer.getInstance().nextInt(_dictionaryList.size());

		return _dictionaryList.get(pos);
	}

	private boolean _isDictionaryWord(String word) {
		return _dictionarySet.contains(word);
	}

	private String[] _unscramble(String word) throws ScramblerException {
		List<String> validWords = new ArrayList<String>();

		String[] words = scramble(word);

		for (int i = 0; i < words.length; i++) {
			if (_dictionarySet.contains(words[i])) {
				validWords.add(words[i]);
			}
		}

		return validWords.toArray(new String[validWords.size()]);
	}

	private static Log _log = LogFactoryUtil.getLog(WordsUtil.class);

	private static WordsUtil _instance = new WordsUtil();

	private List<String> _dictionaryList;
	private Set<String> _dictionarySet;
	private SpellDictionaryHashMap _spellDictionary;

}