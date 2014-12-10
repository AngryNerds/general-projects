package info.angrynerds.wafflecode.mvc;

public enum MVCEventType {
	/**
	 * Thrown by the {@link info.angrynerds.network.NetworkHelper NetworkHelper} to indicate that it received
	 * a message from the server.  It could be of any type.
	 */
	NETWORK_MESSAGE_RECIEVED,
	/**
	 * Thrown by the View to indicate that the user has entered a message and pressed the "Send" button.
	 */
	VIEW_CHAT_SENT,
	/**
	 * Thrown by the Controller to indicate that the controller has received a chat message from the
	 *  {@link info.angrynerds.network.NetworkHelper NetworkHelper}.
	 */
	CONTROLLER_CHAT_RECIEVED,
	/**
	 * Thrown by the View to indicate that the user has inserted text into the code area.
	 */
	VIEW_CODE_INSERTED,
	/**
	 * Thrown by the View to indicate that the user has deleted text from the code area.
	 */
	VIEW_CODE_DELETED,
	/**
	 * Thrown by the Controller to indicate that the controller has received a message from the
	 *  {@link info.angrynerds.network.NetworkHelper NetworkHelper}, saying that
	 * someone has inserted text into the code area.
	 */
	CONTROLLER_CODE_INSERTED,
	/**
	 * Thrown by the Controller to indicate that the controller has received a message from the
	 *  {@link info.angrynerds.network.NetworkHelper NetworkHelper}, saying that someone has deleted text from the
	 *   code area.
	 */
	CONTROLLER_CODE_DELETED,
	/**
	 * Thrown by the Console to indicate that the View should append a message to the JTextArea that is the console.
	 */
	CONSOLE_MESSAGE;
}