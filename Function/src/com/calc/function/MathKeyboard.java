package com.calc.function;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class MathKeyboard {
	
	private KeyboardView kv;
	private Context context;
	private EditText edit;
	
	private Keyboard keyboard_nums;
	private Keyboard keyboard_qwerty;
	private Keyboard keyboard_math;
	private KEYBOARD_MODE keyboard_mode;
	
	public static class CODE {
		public static final int MOVE_LEFT = 57419;
		public static final int MOVE_RIGHT = 57421;
		public static final int MODE_NUM = 57422;
		public static final int MODE_QWERT = 57423;
		public static final int MODE_MATH = 57424;
		public static final int CODE_EXP = 57425;
		public static final int CODE_NULL = 57426;
	}
	
	public static enum KEYBOARD_MODE {
		KEYBOARD_NUM, KEYBOARD_MATH, KEYBOARD_QWERT
	}
	
	public MathKeyboard( KeyboardView kv, Context context, EditText edit ) {
		this.kv = kv;
		this.context = context;
		this.edit = edit;
		
		keyboard_nums = new Keyboard(context, R.layout.key_nums);
		keyboard_qwerty = new Keyboard( context, R.layout.key_qwerty );
		keyboard_math = new Keyboard( context, R.layout.key_math);
		
		kv.setKeyboard( keyboard_qwerty );
		keyboard_mode = KEYBOARD_MODE.KEYBOARD_QWERT;
		
		kv.setEnabled( true );
		kv.setPreviewEnabled( true );
		
		kv.setOnKeyboardActionListener( new KeyboardListener() );
	}
	
	/**
	 * toggle keyboard view
	 */
	public void toggle() {
		int vis = kv.getVisibility();
		kv.setVisibility( vis == View.GONE || vis == View.INVISIBLE ? View.VISIBLE : View.GONE );
	}
	
	public void show() {
		kv.setVisibility( View.VISIBLE );
	}
	
	public void hide() {
		kv.setVisibility( View.GONE );
	}
	
	private class KeyboardListener implements OnKeyboardActionListener {

		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = edit.getText();  
            int start = edit.getSelectionStart();  
            
            switch ( primaryCode ) {
            case Keyboard.KEYCODE_CANCEL:
            	hide();
            	break;
            case Keyboard.KEYCODE_DELETE:
            	if (editable != null && editable.length() > 0) {  
                    editable.delete(start - 1, start);  
                }  
            	break;
            case CODE.MOVE_LEFT:
            	if (start > 0) {  
                    edit.setSelection(start - 1);  
                }  
            	break;
            case CODE.MOVE_RIGHT:
            	if (start < edit.length()) {  
                    edit.setSelection(start + 1);  
                } 
            	break;
            case CODE.MODE_NUM:
            	kv.setKeyboard( keyboard_nums );
        		keyboard_mode = KEYBOARD_MODE.KEYBOARD_NUM;
            	break;
            case CODE.MODE_QWERT:
            	kv.setKeyboard( keyboard_qwerty );
        		keyboard_mode = KEYBOARD_MODE.KEYBOARD_QWERT;
            	break;
            case CODE.MODE_MATH:
            	kv.setKeyboard( keyboard_math );
        		keyboard_mode = KEYBOARD_MODE.KEYBOARD_MATH;
            	break;
            case CODE.CODE_EXP: //自然对数的幂
            	editable.insert(start, "exp");
            	break;
            case CODE.CODE_NULL: //什么也不做
            	break;
            default:
            	editable.insert(start, Character.toString((char)primaryCode));  
            }
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
