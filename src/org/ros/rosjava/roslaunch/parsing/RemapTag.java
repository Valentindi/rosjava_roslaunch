package org.ros.rosjava.roslaunch.parsing;

import java.io.File;
import java.util.Map;

import org.ros.rosjava.roslaunch.util.Util;
import org.w3c.dom.Element;

/**
 * The RemapTag class
 *
 * This class is responsible for parsing and storing the data
 * pertaining to a 'remap' XML tag within a roslaunch file.
 */
public class RemapTag
{
	/** The topic being remapped. */
	private String m_from;
	/** The topic the source topic is remapped to. */
	private String m_to;
	
	/** The list of attributes supported by this tag. */
	private static final Attribute[] SUPPORTED_ATTRIBUTES = new Attribute[]{
		Attribute.From,
		Attribute.To,
	};
	
	/**
	 * Constructor
	 *
	 * Create a RemapTag object from XML.
	 *
	 * @param remap is the XML Element for the remap tag
	 * @param argMap is the args defined in the current scope
	 * @throws a RuntimeException if the 'from' attribute is missing
	 * @throws a RuntimeException if the 'from' attribute is empty
	 * @throws a RuntimeException if the 'to' attribute is missing
	 * @throws a RuntimeException if the 'to' attribute is empty
	 */
	public RemapTag(
			final File file,
			final Element remap,
			final Map<String, String> argMap)
	{
		// Check for unknown attributes 
		Util.checkForUnknownAttributes(file, remap, SUPPORTED_ATTRIBUTES);
		
		// From is a required attribute
		if (!remap.hasAttribute(Attribute.From.val())) {
			throw new RuntimeException(
				"Invalid <remap> tag: <remap> tag is missing rqeuired 'from' attribute");
		}
		m_from = remap.getAttribute(Attribute.From.val());
		m_from = SubstitutionArgs.resolve(m_from, argMap);
		
		// To is a required attribute
		if (!remap.hasAttribute(Attribute.To.val())) {
			throw new RuntimeException(
				"Invalid <remap> tag: <remap> tag is missing required 'to' attribute");
		}
		m_to = remap.getAttribute(Attribute.To.val());
		m_to = SubstitutionArgs.resolve(m_to, argMap);
		
		// From cannot be empty
		if (m_from.length() == 0) {
			throw new RuntimeException(
					"Invalid <remap> tag: remap 'from' attributes cannot be empty");
		}
		
		// To cannot be empty
		if (m_to.length() == 0) {
			throw new RuntimeException(
					"Invalid <remap> tag: remap 'to' attributes cannot be empty");
		}
	}

	/**
	 * Get the 'from' topic being remapped.
	 *
	 * @return the from topic
	 */
	public String getFrom()
	{
		return m_from;
	}
	
	/**
	 * Get the 'to' topic that the 'from' topic is being remapped to.
	 *
	 * @return the to topic
	 */
	public String getTo()
	{
		return m_to;
	}
}
