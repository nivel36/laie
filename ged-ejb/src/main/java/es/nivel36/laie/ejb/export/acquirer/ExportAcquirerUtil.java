package es.nivel36.laie.ejb.export.acquirer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.export.acquirer.ExportData.Item;
import es.nivel36.laie.ejb.export.dto.ExportFieldsOutputBean;
import es.nivel36.laie.ejb.export.dto.ExportFieldsOutputBean.ExportFieldItem;

/**
 * @author Isabel
 *
 */
public final class ExportAcquirerUtil {

	private static class AcquirerItem<T> {

		private final ExportAcquirerI<T, ?> acquirer;

		public AcquirerItem(final ExportAcquirerI<T, ?> acquirer) {
			super();
			Objects.requireNonNull(acquirer);
			this.acquirer = acquirer;
		}

		public ExportAcquirerI<T, ?> getAcquirer() {
			return this.acquirer;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> ExportAcquirerI<T, ?> createInstance(final String className, final Class<T> type) {
		Objects.requireNonNull(className);
		Objects.requireNonNull(type);
		try {
			final Class<?> clazz = Class.forName(className);
			Objects.requireNonNull(clazz);
			// TODO validar tipo
			return (ExportAcquirerI<T, ?>) clazz.getDeclaredConstructor().newInstance();
		} catch (final ClassNotFoundException e) {
			// TODO ivmedina
		} catch (final InstantiationException e) {
			// TODO ivmedina
		} catch (final IllegalAccessException e) {
			// TODO ivmedina
		} catch (final IllegalArgumentException e) {
			// TODO ivmedina
		} catch (final InvocationTargetException e) {
			// TODO ivmedina
		} catch (final NoSuchMethodException e) {
			// TODO ivmedina
		} catch (final SecurityException e) {
			// TODO ivmedina
		}
		return null; // TODO ivmedina quitar
	}

	private static <T> Item exportItemValues(final T object, final List<AcquirerItem<T>> valueAcquires) {
		final Item itemInfo = new Item();
		for (final AcquirerItem<T> item : valueAcquires) {
			final Object value = item.getAcquirer().getValue(object);
			itemInfo.add(value);
		}
		return itemInfo;
	}

	public static <T> ExportData getExportData(final List<T> list, final ExportFieldsOutputBean definition,
			final Class<T> type) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(definition);
		Objects.requireNonNull(type);
		final List<AcquirerItem<T>> valuesAcquiresList = toDefinitionList(definition, type);
		final List<Item> items = new ArrayList<>();
		for (final T item : list) {
			items.add(exportItemValues(item, valuesAcquiresList));
		}
		return new ExportData(toLabelsList(definition), items);
	}

	private static <T> List<AcquirerItem<T>> toDefinitionList(final ExportFieldsOutputBean definition,
			final Class<T> type) {
		final List<AcquirerItem<T>> list = new ArrayList<>();
		for (final ExportFieldItem item : definition.getList()) {
			final ExportAcquirerI<T, ?> acquirer = createInstance(item.getAcquiredClass(), type);
			Objects.requireNonNull(acquirer);
			list.add(new AcquirerItem<T>(acquirer));
		}
		return list;
	}

	private static List<String> toLabelsList(final ExportFieldsOutputBean definition) {
		final List<String> list = new ArrayList<>();
		for (final ExportFieldItem item : definition.getList()) {
			list.add(item.getLiteralId());
		}
		return list;
	}

	private ExportAcquirerUtil() {
		throw new UnsupportedOperationException();
	}
}
