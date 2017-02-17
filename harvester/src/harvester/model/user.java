package harvester.model;

public class user {
	private String id;
	private String userName;
	private String password;// 密码
	private String createTime;// 创建时间
	private String headPortrait;// 头像
	private String gender;// 性别
	private String age;// 年龄
	private String location;// 所在地 字符串 2级:江苏省 南京
	private String lines;// 台词

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLines() {
		return lines;
	}

	public void setLines(String lines) {
		this.lines = lines;
	}

}
