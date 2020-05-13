package sixiuSystem;
//异常 : 选项越界,作答\传入得答案,超过题目得选项数量
public class OptionCrossBoundary extends Exception{
	static final String msg = "Option is cross boundary";
	public OptionCrossBoundary() {
		super(msg);
	}
}
