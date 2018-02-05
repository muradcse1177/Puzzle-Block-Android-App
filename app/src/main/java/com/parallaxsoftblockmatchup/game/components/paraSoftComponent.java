package com.parallaxsoftblockmatchup.game.components;

import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;

public abstract class paraSoftComponent {

	protected paraSoftGameActivity host;
	
	public paraSoftComponent(paraSoftGameActivity ga) {
		host = ga;
	}

	public void reconnect(paraSoftGameActivity ga) {
		host = ga;
	}

	public void disconnect() {
		host = null;
	}
	
}
