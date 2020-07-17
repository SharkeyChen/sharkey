package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Model.RespPageBean;
import com.example.sharkey.Watcher.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/list")
    public RespPageBean getGameList(){
        return gameService.getGameList();
    }
}
