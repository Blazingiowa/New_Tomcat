package test_tomcat_git;

public class UserBean
{
	private String userID;//ユーザーID
	private String roomID;	//ルームID
	private String userNumber;//プレイヤー番号 1 or 2 ?
	private String error;//エラー場所

	//jsonで送るためにセットする
	//setter & getter
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		// TODO 自動生成されたメソッド・スタブ
		this.userID = userID;
		System.out.println("userid通った");
	}


	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		// TODO 自動生成されたメソッド・スタブ
		this.roomID = roomID;
		System.out.println("roomid通った");
	}


	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		// TODO 自動生成されたメソッド・スタブ
		this.userNumber = userNumber;
		System.out.println("user number通った");
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		// TODO 自動生成されたメソッド・スタブ
		this.error = error;
		System.out.println("error通った");
	}
}
