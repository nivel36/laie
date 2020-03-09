package ged.web.core.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.commons.collections4.map.HashedMap;

import ged.ejb.core.model.Obfuscable;

@Named
@ApplicationScoped
public class Navigator {

	private class Page {

		boolean isRedirect;

		PageEnum page;

		Map<String, String> queryParams;

		Page(PageEnum page) {
			this.page = page;
			queryParams = new HashedMap<String, String>();
		}

		Page addQueryIdParam(String value) {
			this.queryParams.put("id", value);
			return this;
		}

		Page addQueryParam(Map<String, String> queryParams) {
			this.queryParams.putAll(queryParams);
			return this;
		}

		Page addQueryRedirectParam() {
			this.isRedirect = true;
			return this;
		}

		private String buildQuery() {
			if (queryParams.isEmpty()) {
				return "";
			}
			final StringBuilder query = new StringBuilder("?");
			final Iterator<Map.Entry<String, String>> it = queryParams.entrySet().iterator();
			while (it.hasNext()) {
				final Map.Entry<String, String> element = it.next();
				final String value = element.getValue();
				final String key = element.getKey();
				query.append(key).append("=").append(value);
				if (it.hasNext()) {
					query.append("&");
				}
			}
			return query.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Page other = (Page) obj;
			return isRedirect == other.isRedirect && page == other.page
					&& Objects.equals(queryParams, other.queryParams);
		}

		String getUrl() {
			final String query = buildQuery();
			if (query.isEmpty()) {
				if (isRedirect) {
					return new StringBuilder(this.page.getUrl()).append("?faces-redirect=true").toString();
				} else {
					return this.page.getUrl();
				}
			} else {
				if (isRedirect) {
					return new StringBuilder(this.page.getUrl()).append(query).append("&faces-redirect=true")
							.toString();
				} else {
					return new StringBuilder(this.page.getUrl()).append(query).toString();
				}
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(isRedirect, page, queryParams);
		}
	}

	public String getRedirectUrl(PageEnum page) {
		return new Page(page).addQueryRedirectParam().getUrl();
	}

	public String getRedirectUrl(PageEnum page, Map<String, String> queryParams) {
		return new Page(page).addQueryParam(queryParams).addQueryRedirectParam().getUrl();
	}

	public String getRedirectUrl(PageEnum page, Obfuscable id) {
		return new Page(page).addQueryIdParam(id.getUid()).addQueryRedirectParam().getUrl();
	}

	public String getUrl(PageEnum page) {
		return new Page(page).getUrl();
	}

	public String getUrl(PageEnum page, Map<String, String> queryParams) {
		return new Page(page).addQueryParam(queryParams).getUrl();
	}

	public String getUrl(PageEnum page, Obfuscable id) {
		return new Page(page).addQueryIdParam(id.getUid()).getUrl();
	}
}
