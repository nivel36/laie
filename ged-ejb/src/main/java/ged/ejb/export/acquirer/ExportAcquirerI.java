package ged.ejb.export.acquirer;

/**
 * @author Isabel
 *
 */
public interface ExportAcquirerI<I,O> {

	O getValue(I item);
}
