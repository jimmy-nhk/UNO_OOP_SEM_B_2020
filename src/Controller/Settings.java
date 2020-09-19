package Controller;

import tools.PathUtils;

import java.io.*;
import java.util.Locale;

public class Settings implements Serializable
{
	private int numberOfAIs;
	private int numberOfStartingCards;
	private int aiSpeed;
	private int volume;
	private boolean allowChallengePlusTwo;
	private boolean allowChallengePlusFourWithTwo;
	private boolean allowChallengePlusFourWithFour;
	private Locale locale;
	
	private static final long serialVersionUID = 1L;	
	
		
	public Settings(int numberOfAIs, int numberOfStartingCards, int aiSpeed, boolean allowChallengePlusTwo,
					boolean allowChallengePlusFourWithTwo, boolean allowChallengePlusFourWithFour,
					Locale locale, int volume)
	{		
		this.numberOfAIs = numberOfAIs;
		this.numberOfStartingCards = numberOfStartingCards;
		this.aiSpeed = aiSpeed;
		this.allowChallengePlusTwo = allowChallengePlusTwo;
		this.allowChallengePlusFourWithTwo = allowChallengePlusFourWithTwo;
		this.allowChallengePlusFourWithFour = allowChallengePlusFourWithFour;
		this.locale = locale;
		this.volume = volume;
	}
	
	public Settings()
	{
		PathUtils.checkFolder(new File(PathUtils.getOSindependentPath() + "/OOP/UNO/"));
		File file = new File(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
		if(!file.exists())
		{
			createStandardValues();
			try
			{
				save();
			}
			catch(Exception e)
			{			
				e.printStackTrace();
			}
		}
	}
	
	public void createStandardValues()
	{		
		numberOfAIs = 1;
		numberOfStartingCards = 7;
		aiSpeed = 2;
		allowChallengePlusTwo = false;
		allowChallengePlusFourWithTwo = false;
		allowChallengePlusFourWithFour = false;
		locale = new Locale("en", "US" );
	}
	
	public void save() throws Exception
	{
		FileOutputStream fileOut = new FileOutputStream(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	}

	public void load() throws Exception
	{
		 FileInputStream fileIn = new FileInputStream(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         Settings loaded = (Settings) in.readObject();
         in.close();
         fileIn.close();
         
         this.numberOfAIs = loaded.getNumberOfAIs();
         this.numberOfStartingCards = loaded.getNumberOfStartingCards();
         this.aiSpeed = loaded.getAiSpeed();
         this.allowChallengePlusTwo = loaded.isAllowChallengePlusTwo();
         this.allowChallengePlusFourWithTwo = loaded.isAllowChallengePlusFourWithTwo();
         this.allowChallengePlusFourWithFour = loaded.isAllowChallengePlusFourWithFour();
         this.locale = loaded.getLocale();
         this.volume = loaded.getVolume();
	}	

	public int getNumberOfAIs()
	{
		return numberOfAIs;
	}

	public Locale getLocale() {
		return locale;
	}

	public int getVolume() {
		return volume;
	}


	public int getNumberOfStartingCards()
	{
		return numberOfStartingCards;
	}

	public int getAiSpeed()
	{
		return aiSpeed;
	}

	public boolean isAllowChallengePlusTwo()
	{
		return allowChallengePlusTwo;
	}

	public boolean isAllowChallengePlusFourWithTwo()
	{
		return allowChallengePlusFourWithTwo;
	}

	public boolean isAllowChallengePlusFourWithFour()
	{
		return allowChallengePlusFourWithFour;
	}	
}
