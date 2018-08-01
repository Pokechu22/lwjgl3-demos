// Port of GLFW input test (https://github.com/glfw/glfw/blob/master/tests/events.c)
package org.lwjgl.demo;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Events {
	private static class Slot {
		public long window;
		public int number;
		public boolean closeable;
	}

	private int counter;
	private StringBuilder log = new StringBuilder();

	private void log(String format, Object... parameters) {
		String message = String.format(format, parameters);
		System.out.print(message);
		log.append(message);
	}

	private Slot[] slots;
	private Slot getSlot(long window) {
		// HACK
		return slots[(int)glfwGetWindowUserPointer(window)];
	}

	private Events(int count) {
		slots = new Slot[count];
	}

	// This is kinda stupid to include
	private String get_key_name(int key) {
		switch (key) {
			// Printable keys
			case GLFW_KEY_A:            return "A";
			case GLFW_KEY_B:            return "B";
			case GLFW_KEY_C:            return "C";
			case GLFW_KEY_D:            return "D";
			case GLFW_KEY_E:            return "E";
			case GLFW_KEY_F:            return "F";
			case GLFW_KEY_G:            return "G";
			case GLFW_KEY_H:            return "H";
			case GLFW_KEY_I:            return "I";
			case GLFW_KEY_J:            return "J";
			case GLFW_KEY_K:            return "K";
			case GLFW_KEY_L:            return "L";
			case GLFW_KEY_M:            return "M";
			case GLFW_KEY_N:            return "N";
			case GLFW_KEY_O:            return "O";
			case GLFW_KEY_P:            return "P";
			case GLFW_KEY_Q:            return "Q";
			case GLFW_KEY_R:            return "R";
			case GLFW_KEY_S:            return "S";
			case GLFW_KEY_T:            return "T";
			case GLFW_KEY_U:            return "U";
			case GLFW_KEY_V:            return "V";
			case GLFW_KEY_W:            return "W";
			case GLFW_KEY_X:            return "X";
			case GLFW_KEY_Y:            return "Y";
			case GLFW_KEY_Z:            return "Z";
			case GLFW_KEY_1:            return "1";
			case GLFW_KEY_2:            return "2";
			case GLFW_KEY_3:            return "3";
			case GLFW_KEY_4:            return "4";
			case GLFW_KEY_5:            return "5";
			case GLFW_KEY_6:            return "6";
			case GLFW_KEY_7:            return "7";
			case GLFW_KEY_8:            return "8";
			case GLFW_KEY_9:            return "9";
			case GLFW_KEY_0:            return "0";
			case GLFW_KEY_SPACE:        return "SPACE";
			case GLFW_KEY_MINUS:        return "MINUS";
			case GLFW_KEY_EQUAL:        return "EQUAL";
			case GLFW_KEY_LEFT_BRACKET: return "LEFT BRACKET";
			case GLFW_KEY_RIGHT_BRACKET: return "RIGHT BRACKET";
			case GLFW_KEY_BACKSLASH:    return "BACKSLASH";
			case GLFW_KEY_SEMICOLON:    return "SEMICOLON";
			case GLFW_KEY_APOSTROPHE:   return "APOSTROPHE";
			case GLFW_KEY_GRAVE_ACCENT: return "GRAVE ACCENT";
			case GLFW_KEY_COMMA:        return "COMMA";
			case GLFW_KEY_PERIOD:       return "PERIOD";
			case GLFW_KEY_SLASH:        return "SLASH";
			case GLFW_KEY_WORLD_1:      return "WORLD 1";
			case GLFW_KEY_WORLD_2:      return "WORLD 2";

			// Function keys
			case GLFW_KEY_ESCAPE:       return "ESCAPE";
			case GLFW_KEY_F1:           return "F1";
			case GLFW_KEY_F2:           return "F2";
			case GLFW_KEY_F3:           return "F3";
			case GLFW_KEY_F4:           return "F4";
			case GLFW_KEY_F5:           return "F5";
			case GLFW_KEY_F6:           return "F6";
			case GLFW_KEY_F7:           return "F7";
			case GLFW_KEY_F8:           return "F8";
			case GLFW_KEY_F9:           return "F9";
			case GLFW_KEY_F10:          return "F10";
			case GLFW_KEY_F11:          return "F11";
			case GLFW_KEY_F12:          return "F12";
			case GLFW_KEY_F13:          return "F13";
			case GLFW_KEY_F14:          return "F14";
			case GLFW_KEY_F15:          return "F15";
			case GLFW_KEY_F16:          return "F16";
			case GLFW_KEY_F17:          return "F17";
			case GLFW_KEY_F18:          return "F18";
			case GLFW_KEY_F19:          return "F19";
			case GLFW_KEY_F20:          return "F20";
			case GLFW_KEY_F21:          return "F21";
			case GLFW_KEY_F22:          return "F22";
			case GLFW_KEY_F23:          return "F23";
			case GLFW_KEY_F24:          return "F24";
			case GLFW_KEY_F25:          return "F25";
			case GLFW_KEY_UP:           return "UP";
			case GLFW_KEY_DOWN:         return "DOWN";
			case GLFW_KEY_LEFT:         return "LEFT";
			case GLFW_KEY_RIGHT:        return "RIGHT";
			case GLFW_KEY_LEFT_SHIFT:   return "LEFT SHIFT";
			case GLFW_KEY_RIGHT_SHIFT:  return "RIGHT SHIFT";
			case GLFW_KEY_LEFT_CONTROL: return "LEFT CONTROL";
			case GLFW_KEY_RIGHT_CONTROL: return "RIGHT CONTROL";
			case GLFW_KEY_LEFT_ALT:     return "LEFT ALT";
			case GLFW_KEY_RIGHT_ALT:    return "RIGHT ALT";
			case GLFW_KEY_TAB:          return "TAB";
			case GLFW_KEY_ENTER:        return "ENTER";
			case GLFW_KEY_BACKSPACE:    return "BACKSPACE";
			case GLFW_KEY_INSERT:       return "INSERT";
			case GLFW_KEY_DELETE:       return "DELETE";
			case GLFW_KEY_PAGE_UP:      return "PAGE UP";
			case GLFW_KEY_PAGE_DOWN:    return "PAGE DOWN";
			case GLFW_KEY_HOME:         return "HOME";
			case GLFW_KEY_END:          return "END";
			case GLFW_KEY_KP_0:         return "KEYPAD 0";
			case GLFW_KEY_KP_1:         return "KEYPAD 1";
			case GLFW_KEY_KP_2:         return "KEYPAD 2";
			case GLFW_KEY_KP_3:         return "KEYPAD 3";
			case GLFW_KEY_KP_4:         return "KEYPAD 4";
			case GLFW_KEY_KP_5:         return "KEYPAD 5";
			case GLFW_KEY_KP_6:         return "KEYPAD 6";
			case GLFW_KEY_KP_7:         return "KEYPAD 7";
			case GLFW_KEY_KP_8:         return "KEYPAD 8";
			case GLFW_KEY_KP_9:         return "KEYPAD 9";
			case GLFW_KEY_KP_DIVIDE:    return "KEYPAD DIVIDE";
			case GLFW_KEY_KP_MULTIPLY:  return "KEYPAD MULTPLY";
			case GLFW_KEY_KP_SUBTRACT:  return "KEYPAD SUBTRACT";
			case GLFW_KEY_KP_ADD:       return "KEYPAD ADD";
			case GLFW_KEY_KP_DECIMAL:   return "KEYPAD DECIMAL";
			case GLFW_KEY_KP_EQUAL:     return "KEYPAD EQUAL";
			case GLFW_KEY_KP_ENTER:     return "KEYPAD ENTER";
			case GLFW_KEY_PRINT_SCREEN: return "PRINT SCREEN";
			case GLFW_KEY_NUM_LOCK:     return "NUM LOCK";
			case GLFW_KEY_CAPS_LOCK:    return "CAPS LOCK";
			case GLFW_KEY_SCROLL_LOCK:  return "SCROLL LOCK";
			case GLFW_KEY_PAUSE:        return "PAUSE";
			case GLFW_KEY_LEFT_SUPER:   return "LEFT SUPER";
			case GLFW_KEY_RIGHT_SUPER:  return "RIGHT SUPER";
			case GLFW_KEY_MENU:         return "MENU";

			default:                    return "UNKNOWN";
		}
	}

	private String get_action_name(int action)
	{
		switch (action)
		{
			case GLFW_PRESS:
				return "pressed";
			case GLFW_RELEASE:
				return "released";
			case GLFW_REPEAT:
				return "repeated";
		}

		return "caused unknown action";
	}

	private String get_button_name(int button)
	{
		switch (button)
		{
			case GLFW_MOUSE_BUTTON_LEFT:
				return "left";
			case GLFW_MOUSE_BUTTON_RIGHT:
				return "right";
			case GLFW_MOUSE_BUTTON_MIDDLE:
				return "middle";
			default:
				return Integer.toString(button);
		}
	}

	private String get_mods_name(int mods)
	{
		String name = "";

		if (mods == 0)
			return " no mods";

		if ((mods & GLFW_MOD_SHIFT) != 0)
			name += " shift";
		if ((mods & GLFW_MOD_CONTROL) != 0)
			name += " control";
		if ((mods & GLFW_MOD_ALT) != 0)
			name += " alt";
		if ((mods & GLFW_MOD_SUPER) != 0)
			name += " super";
		if ((mods & GLFW_MOD_CAPS_LOCK) != 0)
			name += " capslock-on";
		if ((mods & GLFW_MOD_NUM_LOCK) != 0)
			name += " numlock-on";

		return name;
	}

	private void error_callback(int error, long description) {
		log("Error: %s\n", memUTF8(description));
	}

	private void window_pos_callback(long window, int x, int y)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window position: %d %d\n",
				counter++, slot.number, glfwGetTime(), x, y);
	}

	private void window_size_callback(long window, int width, int height)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window size: %d %d\n",
				counter++, slot.number, glfwGetTime(), width, height);
	}

	private void framebuffer_size_callback(long window, int width, int height)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Framebuffer size: %d %d\n",
				counter++, slot.number, glfwGetTime(), width, height);
	}

	private void window_content_scale_callback(long window, float xscale, float yscale)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window content scale: %#.3f %#.3f\n",
				counter++, slot.number, glfwGetTime(), xscale, yscale);
	}

	private void window_close_callback(long window)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window close\n",
				counter++, slot.number, glfwGetTime());

		glfwSetWindowShouldClose(window, slot.closeable);
	}

	private void window_refresh_callback(long window)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window refresh\n",
				counter++, slot.number, glfwGetTime());

		glfwMakeContextCurrent(window);
		glClear(GL_COLOR_BUFFER_BIT);
		glfwSwapBuffers(window);
	}

	private void window_focus_callback(long window, boolean focused)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window %s\n",
				counter++, slot.number, glfwGetTime(),
				focused ? "focused" : "defocused");
	}

	private void window_iconify_callback(long window, boolean iconified)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window was %s\n",
				counter++, slot.number, glfwGetTime(),
				iconified ? "iconified" : "uniconified");
	}

	private void window_maximize_callback(long window, boolean maximized)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Window was %s\n",
				counter++, slot.number, glfwGetTime(),
				maximized ? "maximized" : "unmaximized");
	}

	private void mouse_button_callback(long window, int button, int action, int mods)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Mouse button %d (%s) (with%s) was %s\n",
				counter++, slot.number, glfwGetTime(), button,
				get_button_name(button),
				get_mods_name(mods),
				get_action_name(action));
	}

	private void cursor_position_callback(long window, double x, double y)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Cursor position: %f %f\n",
				counter++, slot.number, glfwGetTime(), x, y);
	}

	private void cursor_enter_callback(long window, boolean entered)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Cursor %s window\n",
				counter++, slot.number, glfwGetTime(),
				entered ? "entered" : "left");
	}

	private void scroll_callback(long window, double x, double y)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Scroll: %#.3f %#.3f\n",
				counter++, slot.number, glfwGetTime(), x, y);
	}

	private void key_callback(long window, int key, int scancode, int action, int mods)
	{
		Slot slot = getSlot(window);
		String name = glfwGetKeyName(key, scancode);

		if (name != null)
		{
			log("%08x to %d at %#.3f: Key 0x%04x Scancode 0x%04x (%s) (%s) (with%s) was %s\n",
					counter++, slot.number, glfwGetTime(), key, scancode,
					get_key_name(key),
					name,
					get_mods_name(mods),
					get_action_name(action));
		}
		else
		{
			log("%08x to %d at %#.3f: Key 0x%04x Scancode 0x%04x (%s) (with%s) was %s\n",
					counter++, slot.number, glfwGetTime(), key, scancode,
					get_key_name(key),
					get_mods_name(mods),
					get_action_name(action));
		}

		if (action != GLFW_PRESS)
			return;

		switch (key)
		{
			case GLFW_KEY_C:
			{
				slot.closeable = !slot.closeable;

				log("(( closing %s ))\n", slot.closeable ? "enabled" : "disabled");
				break;
			}

			case GLFW_KEY_L:
			{
				int state = glfwGetInputMode(window, GLFW_LOCK_KEY_MODS);
				glfwSetInputMode(window, GLFW_LOCK_KEY_MODS, state != 0 ? 0 : 1);

				log("(( lock key mods %s ))\n", state != 0 ? "enabled" : "disabled");
				break;
			}
		}
	}

	private void char_callback(long window, int codepoint)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Character 0x%08x (%s) input\n",
				counter++, slot.number, glfwGetTime(), codepoint,
				(char)codepoint);
	}

	private void char_mods_callback(long window, int codepoint, int mods)
	{
		Slot slot = getSlot(window);
		log("%08x to %d at %#.3f: Character 0x%08x (%s) with modifiers (with%s) input\n",
				counter++, slot.number, glfwGetTime(), codepoint,
				(char)codepoint,
				get_mods_name(mods));
	}

	private void drop_callback(long window, int count, long paths)
	{
		int i;
		Slot slot = getSlot(window);

		log("%08x to %d at %#.3f: Drop input\n",
				counter++, slot.number, glfwGetTime());

		for (i = 0;  i < count;  i++)
			log("  %d: \"%s\"\n", i, memUTF8(paths + (i * org.lwjgl.system.Pointer.POINTER_SIZE)));
	}

	private void monitor_callback(long monitor, int event)
	{
		if (event == GLFW_CONNECTED)
		{
			int[] x = new int[1], y = new int[1], widthMM = new int[1], heightMM = new int[1];
			GLFWVidMode mode = glfwGetVideoMode(monitor);

			glfwGetMonitorPos(monitor, x, y);
			glfwGetMonitorPhysicalSize(monitor, widthMM, heightMM);

			log("%08x at %#.3f: Monitor %s (%dx%d at %dx%d, %dx%d mm) was connected\n",
					counter++,
					glfwGetTime(),
					glfwGetMonitorName(monitor),
					mode.width(), mode.height(),
					x[0], y[0],
					widthMM[0], heightMM[0]);
		}
		else if (event == GLFW_DISCONNECTED)
		{
			log("%08x at %#.3f: Monitor %s was disconnected\n",
					counter++,
					glfwGetTime(),
					glfwGetMonitorName(monitor));
		}
	}

	private void joystick_callback(int jid, int event)
	{
		if (event == GLFW_CONNECTED)
		{
			int axisCount = glfwGetJoystickAxes(jid).capacity();
			int buttonCount = glfwGetJoystickButtons(jid).capacity();
			int hatCount = glfwGetJoystickHats(jid).capacity();

			log("%08x at %#.3f: Joystick %d (%s) was connected with %d axes, %d buttons, and %d hats\n",
					counter++, glfwGetTime(),
					jid,
					glfwGetJoystickName(jid),
					axisCount,
					buttonCount,
					hatCount);
		}
		else
		{
			log("%08x at %#.3f: Joystick %d was disconnected\n",
					counter++, glfwGetTime(), jid);
		}
	}

	public void run() {
		glfwSetErrorCallback(this::error_callback);

		if (!glfwInit())
			System.exit(-1);

		log("Library initialized\n");

		glfwSetMonitorCallback(this::monitor_callback);
		glfwSetJoystickCallback(this::joystick_callback);

		int width  = 640;
		int height = 480;

		for (int i = 0; i < slots.length; i++)
		{
			slots[i] = new Slot();
			slots[i].closeable = true;
			slots[i].number = i + 1;

			String title = String.format("Event Linter (Window %d)", slots[i].number);

			log("Creating windowed mode window %d (%dx%d)\n",
					slots[i].number,
					width, height);

			slots[i].window = glfwCreateWindow(width, height, title, 0, 0);
			if (slots[i].window == 0)
			{
				log("Failed to create window");
				glfwTerminate();
				System.exit(-1);
			}

			glfwSetWindowUserPointer(slots[i].window, i);

			glfwSetWindowPosCallback(slots[i].window, this::window_pos_callback);
			glfwSetWindowSizeCallback(slots[i].window, this::window_size_callback);
			glfwSetFramebufferSizeCallback(slots[i].window, this::framebuffer_size_callback);
			glfwSetWindowContentScaleCallback(slots[i].window, this::window_content_scale_callback);
			glfwSetWindowCloseCallback(slots[i].window, this::window_close_callback);
			glfwSetWindowRefreshCallback(slots[i].window, this::window_refresh_callback);
			glfwSetWindowFocusCallback(slots[i].window, this::window_focus_callback);
			glfwSetWindowIconifyCallback(slots[i].window, this::window_iconify_callback);
			glfwSetWindowMaximizeCallback(slots[i].window, this::window_maximize_callback);
			glfwSetMouseButtonCallback(slots[i].window, this::mouse_button_callback);
			glfwSetCursorPosCallback(slots[i].window, this::cursor_position_callback);
			glfwSetCursorEnterCallback(slots[i].window, this::cursor_enter_callback);
			glfwSetScrollCallback(slots[i].window, this::scroll_callback);
			glfwSetKeyCallback(slots[i].window, this::key_callback);
			glfwSetCharCallback(slots[i].window, this::char_callback);
			glfwSetCharModsCallback(slots[i].window, this::char_mods_callback);
			glfwSetDropCallback(slots[i].window, this::drop_callback);

			glfwMakeContextCurrent(slots[i].window);
			glfwSwapInterval(1);
		}
		GL.createCapabilities();

		log("Main loop starting\n");

		loop: for (;;)
		{
			for (int i = 0; i < slots.length; i++)
			{
				if (glfwWindowShouldClose(slots[i].window))
					break loop;
			}

			glfwWaitEvents();

			// Workaround for an issue with msvcrt and mintty
			System.out.flush();
		}

		glfwSetClipboardString(0, log.toString());
		glfwTerminate();
	}

	public static void main(String[] args) {
		int n = 1;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		new Events(n).run();
	}
}
