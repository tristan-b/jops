package textures;
/**
 * Texture not found
 */
public class TextureNotFoundException extends Exception{
    
    private static final long serialVersionUID = 1602006143541318006L;
    
    TextureNotFoundException(String fn){
        super(fn);
    }
    
}
