package thingswithfriends.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.*;
import android.content.Context;
import android.text.format.Time;

/**
 * 
 * @author pjdesi
 * UserProfile class to hold User's userName and RealName.
 * Intended to be used as a DialogBox in the MainActivity upon creation of the app.
 * Copied from our CampusCreatures project!
 *
 *
 */
public class UserProfile {
	
	private String fileName;
	private String userName;
	private String firstName;
	private String lastName;
	private Boolean hasSignedUp;
	private Boolean isCurrentlyInAGame;
	
	// TODO make a Games class and Friends class
	//private ArrayList<Game> gamesList;
	//private ArrayList<Friends> friendsList;
	//private Game currentUserGame;
	
	public UserProfile(){
		fileName = "userProfile";
		userName = "userName";
		hasSignedUp = false;
		//gamesList = new ArrayList(); 
		//friendsList = new ArrayList();
		isCurrentlyInAGame = false;
		//currentUserGame = null;
	}
	
	//loads the saved user profile in any view and instantiates a new UserProfile objects
	public UserProfile(Context context) {
		this.loadProfile(context);
		UserProfile tempProfile = new UserProfile();
		tempProfile = tempProfile.loadProfile(context);
		fileName = tempProfile.getFileName();
		firstName = tempProfile.getFirstName();
		lastName = tempProfile.getLastName();
		userName = tempProfile.getUserName();
		hasSignedUp = tempProfile.hasSignedUp();
		isCurrentlyInAGame = tempProfile.userCurrentlyInAGame();
	}
	
	public boolean hasSignedUp(){
		return hasSignedUp;
	}
	
	public UserProfile loadProfile(Context context) {
		try {
			FileInputStream fis;
			fis = context.openFileInput("userProfile");
			System.out.println(context);
			try {
				ObjectInputStream is = new ObjectInputStream(fis);
				UserProfile tempProf = (UserProfile) is.readObject();
				is.close();
				return tempProf;
			} catch (StreamCorruptedException e) {
				System.out.println("Here 1");
				e.printStackTrace();
				return this;
			} catch (IOException e) {
				System.out.println("here 2");
				e.printStackTrace();
				return this;
			} catch (ClassNotFoundException e) {
				System.out.println("here 3");
				e.printStackTrace();
				return this;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return this;
		}
	}
	
	public void saveProfile(Context context) {
		//remove template file
		if(userProfileExists(context)) {
			File delFile = new File(context.getFilesDir(),"userProfile");
			delFile.delete();
		}
		
		//create a new file with the same name
		File profile = new File(context.getFilesDir(), "userProfile");
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			System.out.println("gere A");
			System.out.println("firstnameA = " + getFirstName() );
			fos = context.openFileOutput("userProfile", Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(this);
			os.close();
		} catch (FileNotFoundException e) {
			System.out.println("gere B");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("gere C");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean userProfileExists(Context context) {
		File directory = context.getFilesDir();
		File filesList[] = directory.listFiles();
		int listSize = filesList.length;
		for(int i = 0; i < listSize; i++ ) {
			if(filesList[i].getName().equals("userProfile")) { //this will be the name of the file to hold the user info
				System.out.println("there is a file with name 'userProfile'");
				//System.out.println(filesList[i].);
				UserProfile tempProf = new UserProfile();
				tempProf = tempProf.loadProfile(context);
				if(tempProf!=null) {
					System.out.println("as user has signed up = " + tempProf.hasSignedUp());
				}
				return true;
			}
		}
		return false;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getWholeName() {
		return firstName + " " + lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setInitialProfile(String first, String last, String user) {
		if (hasSignedUp) {
			return;
		}
		firstName = first;
		lastName = last;
		userName = user;
		hasSignedUp = true;
	}
	
// TODO change BELOW functions to modify into friendsList	
//	public void addFriend(Friend friend) {
//		party.addPartyMember(creature);
//	}
//	
//	public void removeCreature(BattleCreature creature) {
//		party.removePartyMember(creature);
//	}
	
// Modify to get current games if it is not null
//		public Battle getCurrentUserBattle() {
//			if (isCurrentlyInBattle) {
//				return currentUserBattle;
//			}
//			else {
//				return null;
//			}
//		}
	
	//returns if user was in battle last time the profile was saved
	public boolean userCurrentlyInAGame() {
		return isCurrentlyInAGame;
	}
}
