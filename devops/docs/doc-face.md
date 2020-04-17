# face posts cache
```
IF HAS cache 
    SEND (page, size) TO SERVER
    RECEIVE (current_ims)

    current_posts = []
    miss_ids = []
    FOR(im IN current_ims)
        (id, mtime) = im
        GET cache_post BY id FROM CACHE
        if NOT HAS cache_post
            miss_ids.add(id)
        ELSE IF mtime > cache_post.mtime
            DELETE cache_post BY id FROM CACHE
            miss_ids.add(id)
        ELSE
            current_posts.add(cache_post)

    SEND (miss_ids) TO SERVER
    RECEIVE (miss_posts)
    SAVE miss_posts TO CACHE
    current_posts.addAll(miss_posts)
ELSE
    SEND (page, size) TO SERVER
    RECEIVE (current_posts)
    SAVE current_posts TO CACHE

RENDER current_posts 
```
