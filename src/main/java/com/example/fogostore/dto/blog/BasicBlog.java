package com.example.fogostore.dto.blog;

import java.util.Date;


public interface BasicBlog {
     Integer getId();
     String getTitle();
     String getSlug();
     String getShortDescription();
     String getType();
     String getImage();
     Date getCreatedAt();
     Date getUpdatedAt();
     Boolean getActive();
     Integer getSortIndex();
     Boolean getHot();
}
