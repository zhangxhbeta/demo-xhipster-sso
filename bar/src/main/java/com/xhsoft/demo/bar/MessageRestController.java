package com.xhsoft.demo.bar;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by zhangxh on 16/6/4.
 */
@RestController
public class MessageRestController {

    final List<Message> messages = Collections.synchronizedList(new LinkedList<>());

    @RequestMapping(path = "api/messages", method = RequestMethod.GET)
    List<Message> getMessages(Principal principal) {
        return messages;
    }

    @RequestMapping(path = "api/messages", method = RequestMethod.POST)
    Message postMessage(Principal principal, @RequestBody Message message) {
        message.username = principal.getName();
        message.createdAt = LocalDateTime.now();
        messages.add(0, message);
        return message;
    }
}
