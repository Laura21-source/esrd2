package ru.gbuac.service;

import ru.gbuac.model.User;

import java.util.List;

public interface UserService {

    User getByName(String name);

    int deleteLocalData(int id);

    String deleteLocalDataByUserName(String name);

    List<User> getAllUsers();

    User save(User user);

//    public List<String> getAllUserNames();
//
//    /**
//     * This method is responsible to fetch all the user details as a list of
//     * User object
//     *
//     * @return list of {@link User}
//     */
//    public List<User> getAllUsers();
//
//    /**
//     * This method is responsible to fetch user details of particular user.
//     *
//     * @return user details {@link User}
//     */
//    public User getUserDetails(String userName);
//
//    /**
//     * This method is responsible to fetch user details of particular user as a string.
//     *
//     * @return user detail {@link User}
//     */
//    public String getUserDetail(String userName);
//
//    /**
//     * This method is responsible to authenticate user.
//     *
//     * @return boolean true|false
//     */
//    public boolean authenticate(String base,String userName, String password);
//
//    /**
//     * This method is responsible to update telephone number of user.
//     *
//     * @return boolean true|false
//     */
//    public User updateTelePhone(String userName, String newNumber);
//
//    /**
//     * This method is responsible to create user.
//     */
//    public boolean createUser(User user);
//
//    /**
//     * This method is responsible to delete user.
//     */
//    public boolean remove(String uid);
}
