////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Data;

/**
 * Class Txt.
 * Contains all remarkable texts related to the program.
 * @author Juan Luis
 */
public class Txt {
    
    /**
     * String with info about old versions.
     */
    public static final String OLD_VERSIONS_INFO =
            "Versión 1.0 (15/6/2015):"+
            "\n- Primer programa. Interfaz gráfica."+
            "\n- Nuevo juego: Póker de 5.\n\n"+
            "Versión 1.1 (17/6/2015):"+
            "\n- Nuevo juego: Sota cabrona.\n\n"
            
            ;
    
    /**
     * String with info about last version.
     */
    public static final String LAST_VERSION_INFO =
            "Versión 1.2 (14/10/2015):"+
            "\n- Nuevo juego: Escoba."+
            "\n- Añadida sección Acerca del Programa.\n\n"
    ;
    
    /**
     * Authors' string.
     * Structure (A): {FLAGS,SECTION,AUTHORS...(alphabetically sorted by surnames)}
     * Structure (B): {FLAGS,ENTITY,REASONS...} (thanks, for example)
     * Current flags: MAIN, GRAPHICS, SOUND, GAME, THANKS.
     * For a subsection in a game, use the game name (should have appeared before) as flag.
     * (for example: {"GAME","Póker de 5",...},{"Póker de 5","Diseño gráfico",...})
     */
    public static final String[][] AUTHORS = {
        {"MAIN","Interfaz principal","Juan Luis Suárez Díaz"},
        {"GRAPHICS","Aportación de cartas gráficas","José María Jiménez Mérida","Javier Poyatos Amador","Juan Luis Suárez Díaz"},
        {"GAME","Póker de 5","Juan Luis Suárez Díaz"},
        {"GAME","Sota Cabrona","Juan Luis Suárez Díaz"},
        {"GAME", "Escoba","José María Jiménez Mérida","Javier Poyatos Amador"},
        {"THANKS","Stack Overflow","Aportaciones a la mejora y solución de errores en el código."},
        {"THANKS","www.jfitz.com","Baraja francesa digital de calidad."}    
    };
    
    /**
     * Version's string.
     */
    public static final String VERSION = "v1.2";
    
    /**
     * Copyright's string.
     */
    public static final String COPYRIGHT = "© 2015";
    
    /**
     * Edition's string.
     */
    public static final String EDITION = "Card Games Java Edition";
    
    /**
     * String with program info.
     */
    
    public static final String PROGRAM_INFO = "";/*"¿Buscas un servicio de mensajería divertido, eficaz y sencillo?\n"+
            "Con el nuevo Dropbox MSN, tendrás eso y mucho más.\nInvita a tus amigos, introduce un nombre, ¡y empieza a chatear!";
    */
}
