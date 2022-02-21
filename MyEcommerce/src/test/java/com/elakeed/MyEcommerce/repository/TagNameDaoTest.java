package com.elakeed.MyEcommerce.repository;

import com.elakeed.MyEcommerce.MyEcommerceApplication;
import com.elakeed.MyEcommerce.model.TagName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyEcommerceApplication.class)
public class TagNameDaoTest {

    @Autowired
    private TagNameDAO tagNameDAO;

    @Test
    public void addTagName(){
        TagName savedTagName;
        try {
            savedTagName=tagNameDAO.save(new TagName("addTest"));
        }catch (Exception e){
            savedTagName=null;
        }

        assertNotNull(savedTagName);
    }
    @Test
    public void updateTagName(){
        TagName savedTagName=tagNameDAO.save(new TagName("updateTest"));
        TagName updatedTagName=savedTagName;
        updatedTagName.setTagName("TestUpdated");
        tagNameDAO.save(updatedTagName);
        assertEquals("TestUpdated",tagNameDAO.findById(savedTagName.getTagNameId()).get().getTagName());
    }
    @Test
    public void getTagNameById(){
        TagName savedTagName=tagNameDAO.save(new TagName("getByIdTest"));
        TagName fetchedTagName = tagNameDAO.findById(savedTagName.getTagNameId()).get();
        assertEquals(savedTagName.getTagNameId(),fetchedTagName.getTagNameId());
        assertEquals(savedTagName.getTagName(),fetchedTagName.getTagName());
    }
    @Test
    public void getAllTagNames(){
        tagNameDAO.save(new TagName("Test1"));
        tagNameDAO.save(new TagName("Test2"));
        List<TagName> tagNames= tagNameDAO.findAll();
        assertNotEquals(0,tagNames.size());
    }
    @Test
    public void deleteTagName(){
        TagName savedTagName=tagNameDAO.save(new TagName("deleteTest"));
        boolean isExistBeforeDelete=tagNameDAO.findById(savedTagName.getTagNameId()).isPresent();
        tagNameDAO.deleteById(savedTagName.getTagNameId());
        boolean isExistAfterDelete=tagNameDAO.findById(savedTagName.getTagNameId()).isPresent();
        assertTrue(isExistBeforeDelete);
        assertFalse(isExistAfterDelete);
    }
}
