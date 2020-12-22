package ftn.kts.service;

import static ftn.kts.constants.PictureConstants.DB_ELEMENT_NUM;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_CONTENT;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_NAME;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_PARAM_NAME;
import static ftn.kts.constants.PictureConstants.GET_NULL_ID;
import static ftn.kts.constants.PictureConstants.GET_ONE_ID;
import static ftn.kts.constants.PictureConstants.PROJECT_FOLDER;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.PictureDTO;
import ftn.kts.model.Picture;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PictureServiceIntegrationTest {

    @Autowired
    private PictureService pictureService;
    
    @Test
    public void testGetOneDTO() throws FileNotFoundException, IOException {
    	PictureDTO found = pictureService.getOneDTO(GET_ONE_ID);
        assertEquals(GET_ONE_ID, found.getId());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetOneDTONull() throws FileNotFoundException, IOException {
    	pictureService.getOneDTO(GET_NULL_ID);
    }
    
    @Test
    public void testGetAllDTO() throws FileNotFoundException, IOException {
        List<PictureDTO> found = pictureService.getAllDTO();
        assertEquals(DB_ELEMENT_NUM, found.size());
    }
    
	@Test(expected = NoSuchElementException.class)
    public void testCreateAndDelete() throws IOException {
		MockMultipartFile file = new MockMultipartFile(DB_INSERT_FILE_PARAM_NAME, DB_INSERT_FILE_NAME, "image/jpeg", DB_INSERT_FILE_CONTENT.getBytes());
		
        PictureDTO picture = pictureService.add(file);
        
		String fullPath = picture.getPath();
		Path path = Paths.get(PROJECT_FOLDER + fullPath);
		String found = Files.lines(path).reduce("", String::concat);
        
        assertEquals(DB_INSERT_FILE_CONTENT, found);

		pictureService.delete(picture.getId());
		
		// should throw NoSuchElementException exception because it was just deleted
		pictureService.getOne(picture.getId());
    }
	
    
	@Test(expected = NullPointerException.class)
    public void testCreateNullPicture() throws IOException {
		MockMultipartFile file = null;
        pictureService.add(file);
    }
	
	@Test(expected = NoSuchElementException.class)
    public void testDeleteNotExistingPicture() throws IOException {
        pictureService.delete(GET_NULL_ID);
    }
    
    @Test
    public void testGetOne() {
    	Picture picture = pictureService.getOne(GET_ONE_ID);
        assertEquals(GET_ONE_ID, picture.getId());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetOneNull() {
		pictureService.getOne(GET_NULL_ID);
    }
    
    @Test
    public void testGetAll() {
        List<Picture> found = pictureService.getAll();
        assertEquals(DB_ELEMENT_NUM, found.size());
    }

    @Test
    public void testConvertToDTO() {
    	Set<Picture> pictures = new HashSet<>();
    	pictures.add(pictureService.getOne(GET_ONE_ID));
    	Set<PictureDTO> found = pictureService.convertToDTO(pictures);
    	assertEquals(DB_ELEMENT_NUM, found.size());
    }
    
}
