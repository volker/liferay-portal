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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="SCFrameworkVersionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SCFrameworkVersion table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    SCFrameworkVersionImpl
 * @see    com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion
 * @see    com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionModel
 */
public class SCFrameworkVersionModelImpl extends BaseModelImpl<SCFrameworkVersion> {
	public static final String TABLE_NAME = "SCFrameworkVersion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "frameworkVersionId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "url", new Integer(Types.VARCHAR) },
			

			{ "active_", new Integer(Types.BOOLEAN) },
			

			{ "priority", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table SCFrameworkVersion (frameworkVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,url STRING null,active_ BOOLEAN,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SCFrameworkVersion";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion"),
			true);

	public static SCFrameworkVersion toModel(SCFrameworkVersionSoap soapModel) {
		SCFrameworkVersion model = new SCFrameworkVersionImpl();

		model.setFrameworkVersionId(soapModel.getFrameworkVersionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setUrl(soapModel.getUrl());
		model.setActive(soapModel.getActive());
		model.setPriority(soapModel.getPriority());

		return model;
	}

	public static List<SCFrameworkVersion> toModels(
		SCFrameworkVersionSoap[] soapModels) {
		List<SCFrameworkVersion> models = new ArrayList<SCFrameworkVersion>(soapModels.length);

		for (SCFrameworkVersionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS =
		GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.SCFrameworkVersi_SCProductVers"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion"));

	public SCFrameworkVersionModelImpl() {
	}

	public long getPrimaryKey() {
		return _frameworkVersionId;
	}

	public void setPrimaryKey(long pk) {
		setFrameworkVersionId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_frameworkVersionId);
	}

	public long getFrameworkVersionId() {
		return _frameworkVersionId;
	}

	public void setFrameworkVersionId(long frameworkVersionId) {
		_frameworkVersionId = frameworkVersionId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		_url = url;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public SCFrameworkVersion toEscapedModel() {
		if (isEscapedModel()) {
			return (SCFrameworkVersion)this;
		}
		else {
			SCFrameworkVersion model = new SCFrameworkVersionImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setFrameworkVersionId(getFrameworkVersionId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setName(HtmlUtil.escape(getName()));
			model.setUrl(HtmlUtil.escape(getUrl()));
			model.setActive(getActive());
			model.setPriority(getPriority());

			model = (SCFrameworkVersion)Proxy.newProxyInstance(SCFrameworkVersion.class.getClassLoader(),
					new Class[] { SCFrameworkVersion.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(SCFrameworkVersion.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		SCFrameworkVersionImpl clone = new SCFrameworkVersionImpl();

		clone.setFrameworkVersionId(getFrameworkVersionId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setUrl(getUrl());
		clone.setActive(getActive());
		clone.setPriority(getPriority());

		return clone;
	}

	public int compareTo(SCFrameworkVersion scFrameworkVersion) {
		int value = 0;

		value = getName().compareTo(scFrameworkVersion.getName());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SCFrameworkVersion scFrameworkVersion = null;

		try {
			scFrameworkVersion = (SCFrameworkVersion)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = scFrameworkVersion.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{frameworkVersionId=");
		sb.append(getFrameworkVersionId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", active=");
		sb.append(getActive());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>frameworkVersionId</column-name><column-value><![CDATA[");
		sb.append(getFrameworkVersionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _frameworkVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _url;
	private boolean _active;
	private int _priority;
	private transient ExpandoBridge _expandoBridge;
}