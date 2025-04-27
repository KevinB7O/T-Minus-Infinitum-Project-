
public enum SpaceshipType {
	userSpaceship, eType1, eType2, eType3, eType4, eType5;
	
	public String toString()
	{
		switch(this)
		{
		case userSpaceship: return "spaceship";
		case eType1: return "enemy1";
		case eType2: return "enemy2";
		case eType3: return "enemy3";
		case eType4: return "enemy4";
		case eType5: return "enemy5";
		}
		return "n/a";
	}

}
