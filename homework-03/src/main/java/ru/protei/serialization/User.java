package ru.protei.serialization;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({EventLogger.class})
public class User<T> {

    @XmlElement(name = "id")
    private T id;

    @XmlElement(name = "name")
    private String name;

    private transient String password;

    @XmlElements({
            @XmlElement(name = "EventLogger", type = AdminLogger.class),
            @XmlElement(name = "EventLogger", type = OperatorLogger.class)
    })
    private EventLogger eventLogger;

    @XmlElement(name = "folders")
    private List<String> folders;

    @XmlElement(name = "creator")
    private User creator;

    public User setId(T id) {
        this.id = id;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
        return this;
    }

    public User setFolders(List<String> folders) {
        this.folders = folders;
        return this;
    }

    public User setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User<?> user = (User<?>) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
//        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (eventLogger != null ? !eventLogger.equals(user.eventLogger) : user.eventLogger != null) return false;
        if (folders != null ? !folders.equals(user.folders) : user.folders != null) return false;
        return creator != null ? creator.equals(user.creator) : user.creator == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (eventLogger != null ? eventLogger.hashCode() : 0);
        result = 31 * result + (folders != null ? folders.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", eventLogger=" + eventLogger +
                ", folders=" + folders +
                ", creator=" + creator +
                '}';
    }
}
