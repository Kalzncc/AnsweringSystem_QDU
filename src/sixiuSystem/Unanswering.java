package sixiuSystem;

//异常: 未作答,在这个题目未作答的情况下要求返回getans或得分
public class Unanswering extends Exception{
	static final String msg = "problem unanswering";
	public Unanswering() {
		super(msg);
	}
}
