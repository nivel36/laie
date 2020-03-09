package ged.ejb.export.acquirer.impl;

import ged.ejb.export.acquirer.ExportAcquirerI;
import ged.ejb.user.User;

public final class UserExcelAcquirer {
	
	private UserExcelAcquirer() {}

	public static class NameAcquirer implements ExportAcquirerI<User, String> {

		@Override
		public String getValue(final User item) {
			return item.getName();
		}
	}
	
	public static class SurnameAcquirer implements ExportAcquirerI<User, String> {

		@Override
		public String getValue(final User item) {
			return item.getSurname();
		}
	}
	
	public static class EmailAcquirer implements ExportAcquirerI<User, String> {

		@Override
		public String getValue(final User item) {
			return item.getEmail();
		}
	}
	
	public static class DummyAcquirer implements ExportAcquirerI<User, String> {

		@Override
		public String getValue(final User item) {
			throw new UnsupportedOperationException();
		}
	}
}
