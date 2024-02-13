package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@SpringBootApplication
//@EnableCaching
@AllArgsConstructor
public class DemoApplication {

//    private CacheManager cacheManager;
//    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//  Interface used to indicate that a bean should run when it is contained within a SpringApplication.
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
////            Cache cache = cacheManager.getCache("demoCache");
////            cache.put(4, "Hello");
////            System.out.println("cached::: " + cache.get(1).get());
//
////            userService.getUserById(1);
////            getEhCaches();
//
////            userService.getUser1();
////            getEhCaches();
//
//            userService.getUser();
////            getEhCaches();
//
////            userService.updateUser();
////            getEhCaches();
//
////            userService.deleteUser();
////            getEhCaches();
//
////            userService.deleteAllUsers();
////            getEhCaches();
//
////            PageRequest pageRequest = PageRequest.of(0, 10, JpaSort.unsafe("ASC", "name"));
////            userService.getUserPage(pageRequest);
////            Page<User> ps = (Page<User>) cacheManager.getCache("userPageCache").get("UserPage").get();
////            System.out.println("userPage" + ps.getContent());
//
////            userService.getUserList();
////            getEhCaches();
//
////            userService.getUserMap();
////            getEhCaches();
//        };
//    }
//    
//    public static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable) {
//        Spliterator<T> spliterator = iterable.spliterator();
//        return StreamSupport.stream(spliterator, false);
//    }
//
//    public void getEhCaches() {
//        Stream<String> cacheNames = getStreamFromIterable(cacheManager.getCacheNames());
//        cacheNames.forEach(cacheName ->
//            System.out.println("cached::: " + 
//                    cacheManager.getCache(cacheName).getNativeCache() + "\n"
//                    + cacheManager.getCache(cacheName).getName() + "\n"
////                    + cacheManager.getCache(cacheName).get(1).get() + "\n"
////                    + cacheManager.getCache(cacheName).get("User1").get() + "\n"
////                    + cacheManager.getCache(cacheName).get("User").get() + "\n"
////                    + cacheManager.getCache(cacheName).get("UserList").get() + "\n"
////                    + cacheManager.getCache(cacheName).get("UserMap").get() + "\n"
//                    )
//        );
//    }
}
