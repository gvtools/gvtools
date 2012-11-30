package com.iver.cit.gvsig.fmap.tools.Behavior;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener;

/**
 * <p>
 * When a programmer needs to add a way to permit user to interact with the
 * current object where the graphical information is stored and represented,
 * (see {@link MapControl MapControl}), must analyze and decide the basic
 * <i>behavior</i> nature of the interaction.
 * </p>
 * 
 * <p>
 * That <i>behavior</i> will manage mouse events and generate information and
 * <i>tool</i> events that the associated {@link ToolListener ToolListener} will
 * use to interact with the <code>MapControl</code> object ultimately.
 * </p>
 * 
 * <p>
 * It will be possible also combine more than one <i>behavior</i> for a tool
 * listener, for having a richness tool.
 * </p>
 */
public interface IBehavior {
	/**
	 * <p>
	 * Gets the <code>ToolListener</code> used by this behavior to perform
	 * actions on the associated <code>MapControl</code> object.
	 * </p>
	 * 
	 * @return the <code>ToolListener</code> used by this behavior
	 */
	public ToolListener getListener();

	/**
	 * <p>
	 * Method executed in real-time, when user is working with a tool on the
	 * associated <code>MapControl</code> object, repainting the
	 * <code>MapControl</code>'s image.
	 * </p>
	 * 
	 * <p>
	 * Returns immediately in all cases, even if the complete image has not yet
	 * been loaded.
	 * </p>
	 * 
	 * <p>
	 * This method will be implemented according to the specific nature of each
	 * behavior, and its extra and particular features.
	 * </p>
	 * 
	 * @see Graphics#drawImage(java.awt.Image, int, int,
	 *      java.awt.image.ImageObserver)
	 */
	public void paintComponent(Graphics g);

	/**
	 * <p>
	 * Associates this behavior to a <code>MapControl</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> object to associate
	 * 
	 * @see #getMapControl()
	 */
	public void setMapControl(MapControl mc);

	/**
	 * <p>
	 * Gets the mouse cursor of the tool listener associated to this behavior.
	 * </p>
	 * 
	 * @return the mouse cursor of the tool listener associated
	 */
	public Cursor getCursor();

	/**
	 * <p>
	 * Returns the reference to the <code>MapControl</code> object that this
	 * behavior uses.
	 * </p>
	 * 
	 * @return the <code>MapControl</code> object used this behavior
	 * 
	 * @see #setMapControl(MapControl)
	 */
	public MapControl getMapControl();

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             clicked event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseClicked(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             entered event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseEntered(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             exited event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseExited(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             pressed event</i>, by the <code>IBehavior</code> object
	 */
	public void mousePressed(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             released event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseReleased(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             dragged event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseDragged(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             moved event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseMoved(MouseEvent e) throws BehaviorException;

	/**
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 * 
	 * @throws BehaviorException
	 *             any exception processing the action associated to a <i>mouse
	 *             wheel event</i>, by the <code>IBehavior</code> object
	 */
	public void mouseWheelMoved(MouseWheelEvent e) throws BehaviorException;

}