package sixiuSystem;

//异常: 选项过多,选择题的选项超过26个.
public class OptionNumTooLarge extends Exception{
	static final String msg = "options is too large";
	public OptionNumTooLarge() {
		super(msg);
	}
}
