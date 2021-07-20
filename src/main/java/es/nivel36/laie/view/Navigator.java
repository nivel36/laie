package es.nivel36.laie.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import es.nivel36.laie.core.service.Identifiable;

@Named
@ApplicationScoped
public class Navigator {

	private class Page {

		boolean isRedirect;

		PageEnum pageEnum;

		Map<String, String> queryParams;

		Page(PageEnum pageEnum) {
			this.pageEnum = pageEnum;
			queryParams = new HashMap<>();
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
			return isRedirect == other.isRedirect && pageEnum == other.pageEnum
					&& Objects.equals(queryParams, other.queryParams);
		}

		String getUrl() {
			final String query = buildQuery();
			if (query.isEmpty()) {
				if (isRedirect) {
					return new StringBuilder(this.pageEnum.getUrl()).append("?faces-redirect=true").toString();
				} else {
					return this.pageEnum.getUrl();
				}
			} else {
				if (isRedirect) {
					return new StringBuilder(this.pageEnum.getUrl()).append(query).append("&faces-redirect=true")
							.toString();
				} else {
					return new StringBuilder(this.pageEnum.getUrl()).append(query).toString();
				}
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(isRedirect, pageEnum, queryParams);
		}
	}

	public String getRedirectUrl(PageEnum page) {
		return new Page(page).addQueryRedirectParam().getUrl();
	}

	public String getRedirectUrl(PageEnum page, Map<String, String> queryParams) {
		return new Page(page).addQueryParam(queryParams).addQueryRedirectParam().getUrl();
	}

	public String getRedirectUrl(PageEnum page, Identifiable id) {
		return new Page(page).addQueryIdParam(id.getUid()).addQueryRedirectParam().getUrl();
	}

	public String getUrl(PageEnum page) {
		return new Page(page).getUrl();
	}

	public String getUrl(PageEnum page, Map<String, String> queryParams) {
		return new Page(page).addQueryParam(queryParams).getUrl();
	}

	public String getUrl(PageEnum page, Identifiable id) {
		return new Page(page).addQueryIdParam(id.getUid()).getUrl();
	}
}
