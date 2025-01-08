/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
      // Loop through the users array to find the user with the given name
    for (int i = 0; i < this.userCount; i++) {
        if (this.users[i].getName().equals(name)) {
            return this.users[i]; // Return the User object if found
        }
    }
    return null; // Return null if no user is found
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if(this.userCount == this.users.length){
            return false;
        }
            System.out.println("hi3");

        if (this.getUser(name) != null){
            return false;
        }

        User user1 = new User(name);
        this.users[userCount] = user1;
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
         // Ensure both users exist in the network
         boolean user1Exists = false;
         boolean user2Exists = false;
         System.out.println("hi");
    
    for (int i = 0; i < this.userCount; i++) {
        if (users[i].getName().equalsIgnoreCase(name1)) {
            user1Exists = true;
        }
        if (users[i].getName().equalsIgnoreCase(name2)) {
            user2Exists = true;
        }
        
        // If both users are found, no need to continue checking
        if (user1Exists && user2Exists) {
            break;
        }
    }

    // If either user does not exist, return false
    if (!user1Exists || !user2Exists) {
        return false;
    }

    //find name1 and add name2 as a followee
    for (int i = 0; i < this.userCount; i++) {
        if (users[i].getName().equalsIgnoreCase(name1)) {
            if (users[i].follows(name2)) {
                return false; // User1 is already following User2
            }
            users[i].addFollowee(name2);
            return true; // added followee
        }
    }

    // If we reached here, something went wrong
    return false;
    }
    
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int maxMutualFollowees = 0;
        User mostRecommendedUser = null;
        User user1 = null; // To store the user object of the given name
        
        // Find the User object for the given name
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                user1 = users[i]; // Found the user with the given name
                break;
            }   
        }

        for (int i = 0; i < userCount; i++){
            if (users[i].getName().equalsIgnoreCase(name)) {
                continue;
            }

            // Get the number of mutual followees with "name"
            int mutualFollowees = user1.countMutual(users[i]);

            // Track the user with the maximum mutual followees
            if (mutualFollowees > maxMutualFollowees) {
            maxMutualFollowees = mutualFollowees;
            mostRecommendedUser = users[i]; // Store the user with the max mutual followees
            }
            
        }

        if (mostRecommendedUser == null){
            return null;
        }

        return mostRecommendedUser.getName();   
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int maxFollowed = 0;
        User mostPopular = null;
        int[] followCounter = new int[userCount];  // Array to count how many times each user is followed
        
        // Loop through all users to count how many times each user is followed
        for (int i = 0; i < userCount; i++) {
            for (int j = 0; j < userCount; j++) {
                if (users[i].follows(users[j].getName())) {
                    followCounter[j]++; // Increment the counter for the user that is being followed
                }
            }
        }
    
        // Find the user with the maximum follow count
        for (int i = 0; i < followCounter.length; i++) {
            if (followCounter[i] > maxFollowed) {
                maxFollowed = followCounter[i];
                mostPopular = users[i]; // Update most popular user
            }
        }
        
        // Return the name of the most popular user
        return mostPopular != null ? mostPopular.getName() : null;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        
        int countFollowers = 0;
        
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
               countFollowers++; // Increment the counter for the user that is being followed
            }
        }
        return countFollowers;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String ans = "Network:";
       for (int i = 0 ; i < userCount; i++){
        ans = ans + "\n" + users[i];
       }
       return ans;
    }

}
