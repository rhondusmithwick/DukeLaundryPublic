<?php
/**
 * PHP Web Service for Duke Laundry Iphone App. 
 * This file calls a file servicehelp.php and runs the main method
 * there. This file lives inside the public html directory,
 * while servicehelp.php is outside of it. 
 *
 * @author Rhondu Smithwick
*/

date_default_timezone_set('EST');

require_once("/var/www/servicefiles/servicehelp.php");

main();

