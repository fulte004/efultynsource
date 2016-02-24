using System;
using System.Xml;

namespace SAF.Library.WindowsService.Configuration
{
	/// <summary>
	/// ServiceConfiguration retrieve configuration information
	/// for SAF.WindowService component.
	/// </summary>
	public class ServiceConfiguration
	{
		public Services ServicesXml;
		internal ServiceConfiguration(XmlNode configData)
		{
            
			if (configData != null)
			{
				ServicesXml = Services.Deserialize(configData.OuterXml);
			}
		}
	}

	
}
