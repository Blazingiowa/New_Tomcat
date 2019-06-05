package test_tomcat_git;

public class UserBean
{
	private String userID;//ユーザーID
	private String roomID;	//ルームID
	private String userNumber;//プレイヤー番号 1 or 2 ?

	//setter & getter
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		// TODO 自動生成されたメソッド・スタブ
		this.userID = userID;
	}


	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		// TODO 自動生成されたメソッド・スタブ
		this.roomID = roomID;
	}


	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		// TODO 自動生成されたメソッド・スタブ
		this.userNumber = userNumber;
	}
}
