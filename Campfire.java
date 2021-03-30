// The "Campfire" class.
/*
Bernie Chen, Harry Xiang
Campfire: Draft 1
November 6, 2020
Quad 1 Culminating
*/
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import hsa.Console;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.sound.sampled.*;

//Image importing and font importing from Stack Overflow.
public class Campfire
{
    Console c;           // The output console
    BufferedImage menu = null, splash[] = new BufferedImage [4], match = null, event = null, instructions = null, title = null, morning = null, afternoon = null, meatIcon = null, medIcon = null, highscore = null, gun = null, sword = null, bow = null;
    //The above is all the images that will be imported during splashScreen()
    boolean loading, meatFound, medFound, diseaseCD; //meatFount and medFound are global because they need to be accessed by scenario() and game()
    int day, time, matches, weapon, meat, med, hp; //These variables are global because they need to be accessed by scenario() and game()
    static String gameState = "menu"; //Game state
    Font eightbit, eightbitsmall; //Imported fonts: 8 bit operator
    BufferedReader br; //BufferedReader for file reading
    String[] userName = new String[10]; //Highscore arrays: one for name, one for score.
    String[] score = new String[10];
    int players = 1; //Number of players in highscore table
    String playerScore = "11", temp = "baffest"; //Temporary variables for highscore sorting.
    Campfire () //Class constructor
    {
	c = new Console (30, 75, "Campfire: by Bernie Chen and Harry Xiang");
    }


    public void splashScreen () throws IOException //splash screen method
    {
	String s;
	for (int i = 0 ; i < 4 ; ++i)
	{
	    s = "images\\splash" + i + ".png";
	    try
	    {
		splash [i] = ImageIO.read (new File (s));
	    }
	    catch (IOException e)
	    {
		System.out.println ("Error loading " + s);
	    }
	}
	loading = true;
	splashAnim x = new splashAnim (); //Declares thread splashAnim which runs the splash screen animation
	x.start ();
	//Comment the following try-catch block to see the full animation
	try
	{
	    Thread.sleep (4000);
	}
	catch (InterruptedException e)
	{
	}
	s = "images\\menu.png"; //Image importing
	try
	{
	    menu = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\meat.png";
	try
	{
	    meatIcon = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\med.png";
	try
	{
	    medIcon = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\morning.png";
	try
	{
	    morning = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\afternoon.png";
	try
	{
	    afternoon = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\match.png";
	try
	{
	    match = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\event.png";
	try
	{
	    event = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\instructions.png";
	try
	{
	    instructions = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\title.png";
	try
	{
	    title = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\bow.png";
	try
	{
	    bow = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\highscore.png";
	try
	{
	    highscore = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\gun.png";
	try
	{
	    gun = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	s = "images\\sword.png";
	try
	{
	    sword = ImageIO.read (new File (s));
	}
	catch (IOException e)
	{
	    System.out.println ("Error loading " + s);
	}
	try //Font importing
	{
	    InputStream is = Campfire.class.getResourceAsStream ("fonts\\8bitoperator.ttf");
	    eightbit = Font.createFont (Font.TRUETYPE_FONT, is);
	    eightbitsmall = eightbit.deriveFont (Font.PLAIN, 10);
	    eightbit = eightbit.deriveFont (Font.PLAIN, 20);
	}
	catch (Exception e)
	{
	    System.out.println ("Error loading font.");
	}
	readFile ();
	loading = false;
	while (!loading)
	{
	}
    }


    public void menu () //Menu screen: background, options, and selection
    {
	c.drawImage (menu, 0, 0, null); //Background
	c.drawImage (title, 0, 0, null); //Title
	c.setFont (eightbit);
	char choice = 'x';
	int select = 0;
	while (choice != ' ') //Menu of options: yellow indicates selected choice
	{
	    c.setColor (Color.white);
	    if (select == 0)
		c.setColor (Color.yellow);
	    c.drawString ("Play", 275, 300);
	    c.setColor (Color.white);
	    if (select == 1)
		c.setColor (Color.yellow);
	    c.drawString ("Instructions", 225, 350);
	    c.setColor (Color.white);
	    if (select == 2)
		c.setColor (Color.yellow);
	    c.drawString ("Highscore", 237, 400);
	    c.setColor (Color.white);
	    if (select == 3)
		c.setColor (Color.yellow);
	    c.drawString ("Exit", 275, 450);
	    choice = c.getChar ();
	    if (choice == 'w')
		--select;
	    if (choice == 's')
		++select;
	    select = (select + 4) % 4;
	}
	switch (select)
	{
	    case 0:
		gameState = "game";
		break;
	    case 1:
		gameState = "instructions";
		break;
	    case 2:
		gameState = "highscore";
		break;
	    case 3:
		gameState = "exit";
	}
	if (select == 3) //Transition animation
	{
	    for (int i = 0 ; i <= 100 ; i += 5)
	    {
		c.setColor (new Color (0, 0, 0, 5 + i));
		c.fillRect (0, 0, 600, 600);
	    }
	}
	else if (select == 0)
	{
	    for (int i = 0 ; i <= 30 ; i++)
	    {
		c.setColor (new Color (255, 255, 255, 5 + i * 5));
		c.fillRect (0, 0, 600, 600);
	    }
	    c.clear ();
	}
    }


    public void game () //Starts new game
    {
	meatFound = false;
	medFound = false;
	boolean productive = true;
	day = 1;
	time = 0;
	matches = 6;
	weapon = 0;
	meat = 0;
	med = 0;
	hp = 100; //Variable declaration
	while (matches > 0 || time != 0)
	{
	    if (time == 0 && productive) //Let the games begin!
	    {
		matches--; //Day: x screen
		c.setFont (eightbit);
		c.setColor (Color.black);
		c.fillRect (0, 0, 600, 600);
		c.setColor (Color.white);
		c.drawString ("Day: " + day, 260, 275);
		c.setFont (eightbitsmall);
		c.drawString ("Press a key to continue:", 210, 400);
		c.getChar ();
		diseaseCD=false;
	    }
	    productive = false;
	    switch (time)
	    {
		case 0:
		    c.drawImage (morning, 0, 0, null);
		    break;
		case 1:
		    c.drawImage (afternoon, 0, 0, null);
		    break;
		case 2:
		    c.drawImage (menu, 0, 0, null);
	    }
	    switch (weapon)
	    {
		case 1:
		    c.drawImage (sword, 530, 530, null);
		    break;
		case 2:
		    c.drawImage (bow, 530, 530, null);
		    break;
		case 3:
		    c.drawImage (gun, 530, 530, null);
	    }
	    c.setFont (eightbit);
	    c.setColor (Color.white);
	    c.drawImage (match, 25, 500, null);
	    if (meatFound)
	    {
		c.drawImage (meatIcon, 125, 500, null);
		c.drawString (meat + "", 200, 530);
	    }
	    if (medFound)
	    {
		c.drawImage (medIcon, 250, 500, null);
		c.drawString (med + "", 325, 530);
	    }
	    c.drawString (matches + "", 80, 530);
	    c.drawString ("Day " + day, 25, 50);
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.drawString (hp + "", 480, 90);
	    switch (time) //Show time
	    {
		case 0:
		    c.drawString ("Morning", 25, 75);
		    break;
		case 1:
		    c.drawString ("Afternoon", 25, 75);
		    break;
		case 2:
		    c.drawString ("Evening", 25, 75);
	    }
	    switch (weapon) //Show possessed weapon
	    {
		case 1:
		    c.drawImage (sword, 530, 530, null);
		    break;
		case 2:
		    c.drawImage (bow, 530, 530, null);
		    break;
		case 3:
		    c.drawImage (gun, 530, 530, null);
	    }
	    int select = 0;
	    char choice = 'x';
	    while (choice != ' ') //Options
	    {
		c.setColor (Color.white);
		if (select == 0)
		    c.setColor (Color.yellow);
		c.drawString ("Forage", 160, 300);
		c.setColor (Color.white);
		if (select == 1)
		    c.setColor (Color.yellow);
		c.drawString ("Hunt", 360, 300);
		c.setColor (Color.white);
		if (select == 2)
		    c.setColor (Color.yellow);
		c.drawString ("Cook", 160, 350);
		c.setColor (Color.white);
		if (select == 3)
		    c.setColor (Color.yellow);
		c.drawString ("Rest", 360, 350);
		choice = c.getChar ();
		if (choice == 'd')
		    ++select;
		else if (choice == 'a')
		    --select;
		else if (choice == 's')
		    select += 2;
		else if (choice == 'w')
		    select -= 2;
		select = (select + 4) % 4;
	    }
	    c.setColor (Color.white);
	    c.setFont (eightbitsmall);
	    if (select == 0) //Option 1: Forage (Possible outcomes)
	    {
		productive = true;
		int rand = (int) Math.round (Math.random () * 100);
		if (rand < 10)
		{
		    c.drawString ("You find a roll of gauze, next to a tiny bottle of antibiotics.", 100, 425);
		    med++;
		    medFound = true;
		}
		else if (rand < 30)
		{
		    c.drawString ("You found a match on the ground.", 100, 425);
		    matches++;
		}
		else if (rand < 40)
		{
		    c.drawString ("There is a package of meat, dropped by a recent caravan.", 100, 425);
		    meat += 2;
		    meatFound = true;
		}
		else if (rand < 50)
		{
		    c.drawString ("Your misadventures led you to a pit of poison ivy.", 100, 425);
		    hp -= 10;
		}
		else if (rand < 55)
		{
		    c.drawString ("You found a box of matches in the wreckage of a vehicle.", 100, 425);
		    c.drawString ("Surprisingly, it is well preserved.", 100, 445);
		    matches += 5;
		}
		else if (rand < 60 && matches > 1)
		{
		    c.drawString ("You fumbled and dropped a match.", 100, 425);
		    matches--;
		}
		else if (rand < 62 && weapon < 3)
		{
		    c.drawString ("There is a revolver half-buried in the dirt.", 100, 425);
		    weapon = 3;
		}
		else if (rand < 72 && weapon < 2)
		{
		    c.drawString ("A bow and quiver of arrows were carefully wrapped in cloth.", 100, 425);
		    c.drawString ("The owner has clearly forgot about it.", 100, 445);
		    weapon = 2;
		}
		else if (rand < 84 && weapon < 1)
		{
		    c.drawString ("A rusted sword is embedded in the dirt,", 100, 425);
		    c.drawString ("next to the bones of a long-forgotten race.", 100, 445);
		    weapon = 1;
		}
		else
		    c.drawString ("You come back empty-handed.", 100, 425);
	    }
	    else if (select == 1) //Option 2: Hunt
	    {
		productive = true;
		int rand = (int) Math.round (Math.random () * (weapon + 1));
		if (rand > 0)
		    meatFound = true;
		if (rand == 0)
		    c.drawString ("Unsuccessful hunt. No fruits for your labour.", 100, 425);
		else if (rand == 1)
		    c.drawString ("You gained a scrap of meat.", 100, 425);
		else
		    c.drawString ("You harvested " + rand + " pieces of meat.", 100, 425);
		meat += rand;
	    }
	    else if (select == 3) //Option 4: Rest
	    {
		if (hp == 100)
		    c.drawString ("You have full health.", 100, 425);
		else
		{
		    productive = true;
		    c.drawString ("Your peaceful sleeping healed you.", 100, 425);
		    hp += 10;
		    if (hp > 100)
			hp = 100;
		}
	    }
	    else //Option 3: Cook (Takes as much meat as it can until you are full health.
	    {
		if (hp == 100)
		    c.drawString ("You have full health.", 100, 425);
		else
		{
		    productive = true;
		    if (matches == 0) //You will have to starve, my friend
		    {
			c.drawString ("You don't have any matches to start a fire with,", 100, 425);
			c.drawString ("so you ate the meat raw. You don't feel so good.", 100, 445);
			hp += meat * 2;
			meat = 0;
			if (hp > 100)
			{
			    meat = (hp - 100) / 2;
			    hp = 100;
			}
		    }
		    else
		    {
			c.drawString ("You ate until you couldn't, remembering the", 100, 425);
			c.drawString ("satisfaction and joy of a full stomach.", 100, 445);
			matches--;
			hp += meat * 5;
			meat = 0;
			if (hp > 100)
			{
			    meat = (hp - 100) / 5;
			    hp = 100;
			}
		    }
		}
	    }
	    c.getChar ();
	    switch (time) //Update game background
	    {
		case 0:
		    c.drawImage (morning, 0, 0, null);
		    break;
		case 1:
		    c.drawImage (afternoon, 0, 0, null);
		    break;
		case 2:
		    c.drawImage (menu, 0, 0, null);
	    }
	    switch (weapon)
	    {
		case 1:
		    c.drawImage (sword, 530, 530, null);
		    break;
		case 2:
		    c.drawImage (bow, 530, 530, null);
		    break;
		case 3:
		    c.drawImage (gun, 530, 530, null);
	    }
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawImage (match, 25, 500, null);
	    if (meatFound)
	    {
		c.drawImage (meatIcon, 125, 500, null);
		c.drawString (meat + "", 200, 530);
	    }
	    if (medFound)
	    {
		c.drawImage (medIcon, 250, 500, null);
		c.drawString (med + "", 325, 530);
	    }
	    c.drawString (matches + "", 80, 530);
	    c.drawString ("Day " + day, 25, 50);
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.drawString (hp + "", 480, 90);
	    switch (time)
	    {
		case 0:
		    c.drawString ("Morning", 25, 75);
		    break;
		case 1:
		    c.drawString ("Afternoon", 25, 75);
		    break;
		case 2:
		    c.drawString ("Evening", 25, 75);
	    }
	    switch (weapon)
	    {
		case 1:
		    c.drawImage (sword, 530, 530, null);
		    break;
		case 2:
		    c.drawImage (bow, 530, 530, null);
		    break;
		case 3:
		    c.drawImage (gun, 530, 530, null);
	    }
	    if (productive)
	    {
		if (hp > 0)
		    scenario ();
		if (time == 2)
		{
		    time = 0;
		    day++;
		}
		else
		    time++;
	    }
	    if (hp <= 0) //Pass out screen
	    {
		c.setFont (eightbit);
		c.setColor (Color.black);
		c.fillRect (0, 0, 600, 600);
		c.setColor (Color.white);
		c.drawString ("You passed out from your injuries.", 75, 275);
		c.drawString ("Your camp was raided in the following", 50, 300);
		c.drawString ("hours.", 260, 325);
		c.setFont (eightbitsmall);
		c.drawString ("Press a key to continue:", 210, 400);
		matches = (int) (Math.random () * matches);
		meat = (int) (Math.random () * meat);
		med = (int) (Math.random () * med);
		weapon = 0;
		hp = 20;
		c.getChar ();
	    }
	}
	c.setFont (eightbit); //Game Over screen
	c.setColor (Color.black);
	c.fillRect (0, 0, 600, 600);
	c.setColor (Color.white);
	c.drawString ("You have no matches for the night.", 75, 275);
	c.drawString ("The darkness consumes you, leaving", 50, 300);
	c.drawString ("nothing behind.", 220, 325);
	c.drawString ("You survived: "+(day-1)+" days.", 75, 360);
	c.setFont (eightbitsmall);
	c.drawString ("Press a key to continue:", 210, 400);
	c.getChar ();
	c.setFont (eightbit);
	c.setColor (Color.black);
	c.fillRect (0, 0, 600, 600);
	c.setColor (Color.white);
	c.drawString ("Please enter your name:", 50, 300); //Input for highscore
	c.setTextBackgroundColor (Color.black);
	c.setTextColor (Color.white);
	c.setCursor (20, 15);
	temp = c.readString ();
	playerScore = "" + (day-1);
	gameState = "menu";
    }


    public void scenario ()
    {
	int rand = (int) (Math.random () * 100); //RNG strikes again!
	int select = 0;
	char choice;
	if (rand <= 30) 
	{
	    c.drawImage (event, 0, 0, null); //Trader: 30% chance of occurence
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("The Trader", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("You encounter a wanderer. They seem to have some things to trade.", 60, 210);
	    while (select != 3) //Loops until you leave.
	    {
		select = 0;
		choice = 'x';
		while (choice != ' ')
		{
		    c.setColor (Color.white);
		    if (select == 0)
			c.setColor (Color.yellow);
		    c.drawString ("1 match -> 3 meat", 100, 300);
		    c.setColor (Color.white);
		    if (select == 1)
			c.setColor (Color.yellow);
		    c.drawString ("5 matches -> 1 med", 360, 300);
		    c.setColor (Color.white);
		    if (select == 2)
			c.setColor (Color.yellow);
		    c.drawString ("5 meat -> 1 match", 100, 350);
		    c.setColor (Color.white);
		    if (select == 3)
			c.setColor (Color.yellow);
		    c.drawString ("Leave", 360, 350);
		    choice = c.getChar ();
		    if (choice == 'd')
			++select;
		    else if (choice == 'a')
			--select;
		    else if (choice == 's')
			select += 2;
		    else if (choice == 'w')
			select -= 2;
		    select = (select + 4) % 4;
		}
		String s = "";
		if (select == 0)
		{
		    if (matches < 1)
			s = "You don't have enough matches.";
		    else
		    {
			s = "You traded one match for a package of three pieces of meat.";
			matches--;
			meat += 3;
			meatFound = true;
		    }
		}
		else if (select == 1)
		{
		    if (matches < 5)
			s = "You don't have enough matches.";
		    else
		    {
			s = "You traded five matches for gauze and a bottle of pills.";
			matches -= 5;
			med++;
			medFound = true;
		    }
		}
		else if (select == 2)
		{
		    if (meat < 5)
			s = "You don't have enough meat.";
		    else
		    {
			s = "You traded 5 scraps of meat for a match. Worth.";
			meat -= 5;
			matches++;
		    }
		}
		else
		{
		    s = "You left, trying to remember how to get back to the campfire.";
		}
		c.drawImage (event, 0, 0, null);
		c.setColor (Color.white);
		c.setFont (eightbit);
		c.drawString ("The Trader", 60, 180);
		c.setFont (eightbitsmall);
		c.drawString (s, 60, 210);
		c.drawString ("Press a key to continue.", 60, 250);
		c.getChar ();
	    }
	}
	else if (rand <= 35 && day > 10 && !diseaseCD) //Plague: the ultimate sickness (5%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Plague", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("You find yourself writhing in tortuous agony. The pain is unbearable.", 60, 210);
	    select = 0;
	    choice = 'x';
	    diseaseCD=true;
	    while (choice != ' ')
	    {
		c.setColor (Color.white);
		if (select == 0)
		    c.setColor (Color.yellow);
		c.drawString ("Pop a pill (3 meds needed).", 100, 300);
		c.setColor (Color.white);
		if (select == 1)
		    c.setColor (Color.yellow);
		c.drawString ("What doesn't kill you makes you stronger :)", 100, 350);
		c.setColor (Color.white);
		choice = c.getChar ();
		if (choice == 's')
		    ++select;
		else if (choice == 'w')
		    --select;
		select = (select + 2) % 2;
	    }
	    if (select == 0)
	    {
		if (med < 3)
		{
		    s = "You don't have medicine.";
		    c.drawImage (event, 0, 0, null);
		    c.setColor (Color.white);
		    c.setFont (eightbit);
		    c.drawString ("Plague", 60, 180);
		    c.setFont (eightbitsmall);
		    c.drawString (s, 60, 210);
		    c.drawString ("Press a key to continue.", 60, 250);
		    c.getChar ();
		    select = 1;
		}
		else
		{
		    s = "Slowly but surely, you begin to heal (not medically accurate).";
		    med -= 3;
		    hp = 100;
		}
	    }
	    if (select == 1)
	    {
		s = "Pain overwhelms you as you twitch on the floor, waiting it out.";
		hp = 0;
	    }
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Plague", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("Press a key to continue.", 60, 250);
	    c.getChar ();
	}
	else if (rand <= 45 && day > 5 && !diseaseCD) //Disease: moderate (10%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Disease", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("Hives pop up on your skin. You don't feel well.", 60, 210);
	    select = 0;
	    choice = 'x';
	    diseaseCD=true;
	    while (choice != ' ')
	    {
		c.setColor (Color.white);
		if (select == 0)
		    c.setColor (Color.yellow);
		c.drawString ("Use some medicine (needs 2 medicine)", 100, 300);
		c.setColor (Color.white);
		if (select == 1)
		    c.setColor (Color.yellow);
		c.drawString (":) It's just the flu...", 100, 350);
		c.setColor (Color.white);
		choice = c.getChar ();
		if (choice == 's')
		    ++select;
		else if (choice == 'w')
		    --select;
		select = (select + 2) % 2;
	    }
	    if (select == 0)
	    {
		if (med < 2)
		{
		    s = "You don't have medicine.";
		    c.drawImage (event, 0, 0, null);
		    c.setColor (Color.white);
		    c.setFont (eightbit);
		    c.drawString ("Disease", 60, 180);
		    c.setFont (eightbitsmall);
		    c.drawString (s, 60, 210);
		    c.drawString ("Press a key to continue.", 60, 250);
		    c.getChar ();
		    select = 1;
		}
		else
		{
		    s = "You didn't die. Maybe miracles do happen.";
		    med -= 2;
		    hp = 100;
		}
	    }
	    if (select == 1)
	    {
		s = "It was not the flu. You end up with high fever and coughing.";
		hp -= 50;
		if (hp < 0)
		    hp = 0;
	    }
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Disease", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("Press a key to continue.", 60, 250);
	    c.getChar ();
	}
	else if (rand <= 60 && day > 1 && !diseaseCD) //Infection: nonsevere (15%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Infection", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("You're worried a cut on your arm won't fare well.", 60, 210);
	    select = 0;
	    choice = 'x';
	    diseaseCD=true;
	    while (choice != ' ')
	    {
		c.setColor (Color.white);
		if (select == 0)
		    c.setColor (Color.yellow);
		c.drawString ("Disinfect it (needs 1 medicine)", 100, 300);
		c.setColor (Color.white);
		if (select == 1)
		    c.setColor (Color.yellow);
		c.drawString ("\"Be a brave kid\"", 100, 350);
		c.setColor (Color.white);
		choice = c.getChar ();
		if (choice == 's')
		    ++select;
		else if (choice == 'w')
		    --select;
		select = (select + 2) % 2;
	    }
	    if (select == 0)
	    {
		if (med < 1)
		{
		    s = "You don't have medicine.";
		    c.drawImage (event, 0, 0, null);
		    c.setColor (Color.white);
		    c.setFont (eightbit);
		    c.drawString ("Infection", 60, 180);
		    c.setFont (eightbitsmall);
		    c.drawString (s, 60, 210);
		    c.drawString ("Press a key to continue.", 60, 250);
		    c.getChar ();
		    select = 1;
		}
		else
		{
		    s = "You don't even notice anything.";
		    med -= 1;
		    hp = 100;
		}
	    }
	    if (select == 1)
	    {
		s = "You were right. Your arm is now in pain!";
		hp -= 20;
		if (hp < 0)
		    hp = 0;
	    }
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Infection", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("Press a key to continue.", 60, 250);
	    c.getChar ();
	}
	else if (rand <= 65 && day > 8) //Beast: severe attack (5%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Beast", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("A beast leaps out from the brush, its claws bloodied.", 60, 210);
	    c.setColor (Color.yellow);
	    c.drawString ("Fight!", 100, 300);
	    choice = 'x';
	    while (choice != ' ')
	    {
		choice = c.getChar ();
	    }
	    if (weapon == 0)
	    {
		s = "No contest. The monster leaves you broken and bleeding.";
		hp-=70;
	    }
	    else if (weapon == 1)
	    {
		s = "It took a few tries to stab the sword through. You ended half-dead.";
		hp-=50;
	    }
	    else if (weapon == 2)
	    {
		s = "A dozen arrows pierced the hide until it died. You weren't spared.";
		hp-=30;
	    }
	    else
	    {
		s = "Headshot. You killed with ease.";
	    }
	    if (hp < 0)
		hp = 0;
	    meat += weapon*2;
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Beast", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("You harvested "+(weapon*2)+" meat.", 60, 250);
	    c.drawString ("Press a key to continue.", 60, 350);
	    c.getChar ();
	    
	}
	else if (rand <= 75 && day > 4)//Monster: moderate (10%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Monster", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("A small monster falls from the trees, shrieking.", 60, 210);
	    c.setColor (Color.yellow);
	    c.drawString ("Fight!", 100, 300);
	    choice = 'x';
	    while (choice != ' ')
	    {
		choice = c.getChar ();
	    }
	    if (weapon == 0)
	    {
		s = "A few hours of fighting later, you kill it. You are wounded.";
		hp-=50;
	    }
	    else if (weapon == 1)
	    {
		s = "You were victorious, with a few new scars.";
		hp-=25;
	    }
	    else
	    {
		s = "One second alive, another second dead. GG EZ";
	    }
	    meat += weapon+1;
	    if (hp < 0)
		hp = 0;
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Monster", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("You harvested "+(weapon+1)+" meat.", 60, 250);
	    c.drawString ("Press a key to continue.", 60, 350);
	    c.getChar ();
	}
	else if (rand <= 90 && day > 2) //Scavenger: easy mode (15%)
	{
	    String s = "";
	    c.drawImage (event, 0, 0, null);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString ("Scavenger", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("A small animal approaches, eyes rabid.", 60, 210);
	    c.setColor (Color.yellow);
	    c.drawString ("Fight!", 100, 300);
	    choice = 'x';
	    while (choice != ' ')
	    {
		choice = c.getChar ();
	    }
	    if (weapon == 0)
	    {
		s = "You sustain a scratch. The animal sustained death.";
		hp-=5;
	    }
	    else
	    {
		s = "Such a nuisance. Its carcass hangs on the wall.";
	    }
	    meat += weapon;
	    if (hp < 0)
		hp = 0;
	    c.setColor (Color.black);
	    c.fillRect (450, 50, 100, 60);
	    c.fillRect (470, 30, 60, 100);
	    c.setColor (Color.red);
	    c.fillRect (450, 50, hp, 60);
	    if (hp >= 80)
		c.fillRect (470, 30, 60, 100);
	    else if (hp >= 20)
		c.fillRect (470, 30, hp - 20, 100);
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Scavenger", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString (s, 60, 210);
	    c.drawString ("You harvested "+(weapon)+" meat.", 60, 250);
	    c.drawString ("Press a key to continue.", 60, 350);
	    c.getChar ();
	}
	else //I had no more ideas.
	{
	    c.setColor (Color.white);
	    c.setFont (eightbit);
	    c.drawString (hp + "", 480, 90);
	    c.drawImage (event, 0, 0, null);
	    c.drawString ("Nothing", 60, 180);
	    c.setFont (eightbitsmall);
	    c.drawString ("Nothing happens. It's a miracle. Or is it?", 60, 210);
	    c.drawString ("Press a key to continue.", 60, 350);
	    c.getChar ();
	}
    }


    public void instructions () //Instructions on how to play
    {
	c.drawImage (instructions, 0, 0, null);
	c.setColor (Color.yellow);
	c.drawString ("Exit", 275, 450);
	c.setColor (Color.white);
	c.setFont (eightbitsmall);
	c.drawString ("You are trapped in the words of an unknown land, only armed with", 60, 175);
	c.drawString ("a box of five matches. Try to survive as long as possible, given", 60, 200);
	c.drawString ("disease, beasts, and scavengers don't get you first.", 60, 225);
	c.drawString ("When you don't have any matches for the night, it's game over.", 60, 250);
	c.drawString ("WASD to move between options            Space to choose options", 60, 325);
	c.drawString ("", 400, 325);
	char input = 'x';
	while (input != ' ')
	    input = c.getChar ();
	gameState = "menu";
    }


    public void highscore () throws IOException
    {
	c.drawImage (highscore, 0, 0, null);     //create highscore image (my art skills baf)
	c.setColor (Color.yellow);
	c.drawString ("Exit", 275, 560);
	c.setColor (Color.white);
	c.setFont (eightbitsmall);
	readFile ();
	printHighscore ();
	c.drawString ("Ranking", 100, 175);
	c.drawString ("Username", 250, 175);
	c.drawString ("Score", 400, 175);
	char input = 'x';
	while (input != ' ')
	    input = c.getChar ();
	gameState = "menu";
    }


    public void readFile () throws IOException          //reads file
    {
	boolean invalid = true;
	String fileName;
	String playerString;

	String line = ""; //variable for reading data from the file
	//try to open the file and read from it
	//try

	br = new BufferedReader (new FileReader ("Highscore.txt"));
	playerString = br.readLine (); //read the first line of data
	players = Integer.parseInt (playerString);
	for (int i = 0 ; i < players; i++)
	{
	    line = br.readLine ();
	    userName [i] = line;
	    line = br.readLine ();
	    score [i] = line;
	}
    }


    public void editFile () throws IOException       //edits highscore ranking (haven't tested up to 10 yet)
    {
	if (players < 10) players++;
	int count = 0;
	while (count<10&&score[count]!=null&&Integer.parseInt(score[count])>=Integer.parseInt(playerScore)) count++;
	for (int i = 8; i >= count; --i) {
	    score[i+1] = score[i];
	    userName[i+1] = userName[i];
	}
	if (count<10)
	{
	    score[count] = playerScore;
	    userName[count] = temp;
	}
    }


    public void printHighscore ()           //displays class
    {
	for (int i = 0 ; i < players; ++i)
	{
	    c.drawString (String.valueOf (i + 1), 100, 175 + 30 * (i + 1));
	    c.drawString (userName [i], 250, 175 + 30 * (i + 1));
	    c.drawString (score [i], 400, 175 + 30 * (i + 1));
	}
    }


    public void writeFile () throws IOException             //probably should write file after player finishes the game
    {

	PrintWriter output; // definition for a PrintWriter object

	//try to open the file
	output = new PrintWriter (new FileWriter ("Highscore.txt", false));
	//this loop will write all the data in the array to the file, with each element on a new line
	output.println (players);
	for (int i = 0 ; i < players ; i++)
	{

	    output.println (userName [i]);
	    output.println (score [i]);
	}
	output.close (); //this line will save the file
    }


    public void exit ()
    {
	c.setFont(eightbit);
	c.setColor(Color.white);
	c.drawString("The End.", 350, 300);
	c.setFont(eightbitsmall);
	c.drawString("Thank you for playing.", 50, 350);
	c.drawString("Campfire: by Bernie Chen and Harry Xiang", 50, 400);
	c.drawString("Visuals by Bernie Chen", 50, 450);
	c.drawString("Press a key to continue.", 50, 500);
	c.getChar();
	c.close ();
    }


    public static void main (String[] args) throws InterruptedException, IOException
    {
	Campfire f = new Campfire ();
	f.splashScreen ();
	while (!gameState.equals ("exit"))
	{
	    if (gameState.equals ("menu"))
		f.menu ();
	    else if (gameState.equals ("game"))
	    {
		f.game ();
		f.editFile ();
		f.writeFile ();
	    }
	    else if (gameState.equals ("instructions"))
		f.instructions ();
	    else if (gameState.equals ("highscore"))
		f.highscore ();
	}
	f.exit ();
    } // main method


    class splashAnim extends Thread
    {
	public void run ()
	{
	    while (loading)
	    {
		int current = (int) (System.currentTimeMillis () % 1000); //Animation for splash.
		c.drawImage (splash [current / 250], 0, 0, null);
		c.setColor(Color.white);
		c.drawString("Tip: use WASD to move between options and space to select.", 50, 300);
	    }
	    for (int i = 0 ; i <= 250 ; i += 5)
	    {
		c.setColor (new Color (0, 0, 0, 5 + i));
		c.fillRect (25, 525, 150, 55);
		c.fillRect (380, 325, 150, 275);
		try
		{
		    sleep (10);
		}
		catch (InterruptedException e)
		{
		}
	    }
	    loading = true;
	}
    }
} // Campfire class


