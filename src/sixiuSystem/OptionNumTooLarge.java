package sixiuSystem;

//�쳣: ѡ�����,ѡ�����ѡ���26��.
public class OptionNumTooLarge extends Exception{
	static final String msg = "options is too large";
	public OptionNumTooLarge() {
		super(msg);
	}
}
