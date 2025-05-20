package es.nivel36.laie.web.view.client.contact;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.ejb.core.model.Page;
import jakarta.inject.Inject;

public class ContactLazyDataModel extends LazyDataModel<Contact> {

	private static final long serialVersionUID = -4218300046788680778L;

	private Client client;
	private @Inject transient ContactService contactService;

	@Override
	public int count(final Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(client);
		return (int) contactService.countContactsByClient(client);
	}

	@Override
	public List<Contact> load(final int first, final int pageSize, final Map<String, SortMeta> sortBy,
			final Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(client);
		return contactService.findContactsByClient(client, Page.of(first, pageSize));
	}
	
	@Override
    public Contact getRowData(String rowKey) {
		return contactService.findContactById(Long.parseLong(rowKey));
      }

    @Override
    public String getRowKey(Contact contact) {
        return String.valueOf(contact.getId());
    }

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = Objects.requireNonNull(contactService);
	}
		
}