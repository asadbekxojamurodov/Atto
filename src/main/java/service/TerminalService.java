package service;

import dto.TerminalDto;
import enums.Status;
import repository.TerminalRepository;

import java.time.LocalDateTime;

public class TerminalService {
    TerminalRepository terminalRepository = new TerminalRepository();

    public boolean createTerminal(TerminalDto terminalDto) {

        TerminalDto terminal = new TerminalDto();

        terminal.setCode(terminal.getCode());
        terminal.setAddress(terminalDto.getAddress());
        terminal.setStatusTerminal(Status.ACTIVE);
        terminal.setCreatedDate(LocalDateTime.now());


        return  terminalRepository.createTerminal(terminal);

    }
}
