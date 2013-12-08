package sample.customer.biz.domain;

import javax.validation.constraints.AssertFalse;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class Customer implements java.io.Serializable {

	private int id;

    @NotBlank
    @Length(max = 20)
    private String name;

    @NotBlank
    @Length(max = 100)
    private String address;

    @NotBlank
    @Email
    private String emailAddress;

    public Customer(String name, String address, String emailAddress) {
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    @AssertFalse(message = "{errors.ngemail}")
    public boolean isNgEmail() {
        // ドメイン名が「ng.foo.baz」であれば使用不可のアドレスと見なす
        return emailAddress.matches(".*@ng.foo.baz$");
    }

    public boolean isFreeEmail() {
        // ドメイン名が「free.foo.baz」であればフリーメールのアドレスと見なす
    	return emailAddress.matches(".*@free.foo.baz$");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Customer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer [id=%s, name=%s, address=%s, emailAddress=%s]",
                id, name, address, emailAddress);
    }

	private static final long serialVersionUID = 3428490997353904743L;
}
