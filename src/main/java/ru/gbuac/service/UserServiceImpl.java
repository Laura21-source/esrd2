package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.UserRepository;
import ru.gbuac.model.User;
import ru.gbuac.util.exception.NotFoundException;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = true)
    @Qualifier(value = "ldapTemplate")
    private LdapTemplate ldapTemplate;


    @Override
    public List<String> getAllLdapUsers() {
        LdapQuery query = query().base("ou=users");
        List<String> list = ldapTemplate.list(query.base());
        return ldapTemplate.search(query().base("ou=users").where("objectClass").is("person"), new SingleAttributesMapper());
    }

    @Override
    public User getByName(String name) throws NotFoundException {
        return userRepository.getByName(name);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

//    @Override
//    public List<User> getAllLdapUsers() {
//        SearchControls controls = new SearchControls();
//        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        return ldapTemplate.search(DistinguishedName.EMPTY_PATH, "(objectclass=person)", controls, new UserAttributesMapper());
//    }



    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user, int id) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        return checkNotFoundWithId(userRepository.save(user), id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        userRepository.delete(id);
    }

//    @Data
//    private class UserAttributesMapper implements AttributesMapper<User> {
//
//        @Override
//        public User mapFromAttributes(Attributes attributes) throws NamingException {
//            User user;
//            if (attributes == null) {
//                return null;
//            }
//            user = new User();
//            user.setCn(attributes.get("cn").get().toString());
//
//            if (attributes.get("userPassword") != null) {
//                String userPassword = null;
//                try {
//                    userPassword = new String((byte[]) attributes.get("userPassword").get(), "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                }
//                user.setUserPassword(userPassword);
//            }
//            if (attributes.get("uid") != null) {
//                user.setUid(attributes.get("uid").get().toString());
//            }
//            if (attributes.get("sn") != null) {
//                user.setSn(attributes.get("sn").get().toString());
//            }
//            if (attributes.get("postalAddress") != null) {
//                user.setPostalAddress(attributes.get("postalAddress").get().toString());
//            }
//            if (attributes.get("telephoneNumber") != null) {
//                user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());
//            }
//            return user;
//        }
//}
    private class SingleAttributesMapper implements AttributesMapper<String> {

        @Override
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            Attribute cn = attrs.get("cn");
            return cn.toString();
        }
    }
}

