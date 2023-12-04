package service;

import dto.TerminalDto;
import enums.Status;
import repository.TerminalRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TerminalService {
    TerminalRepository terminalRepository = new TerminalRepository();


    public void updateTerminalByCode(TerminalDto terminal) {
        TerminalDto exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist == null) {
            System.out.println("Terminal not found");
            return;
        }

        terminalRepository.updateTerminal(terminal);
        System.out.println("terminal address updated");
    }

    public void changeTerminalStatus(String code) {
        TerminalDto terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal code not exists");
            return;
        }

        if (terminal.getStatusTerminal().equals(Status.ACTIVE)) {
            terminalRepository.changeTerminalStatus(code, Status.BLOCKED);
            System.out.println("Status changed");

        } else if (terminal.getStatusTerminal().equals(Status.BLOCKED)) {
            terminalRepository.changeTerminalStatus(code, Status.ACTIVE);
            System.out.println("Status changed");
        }

    }

    public void delete(String code) {
        TerminalDto terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return ;
        }
         terminalRepository.delete(terminal);
    }


    public void adminCreateTerminal(TerminalDto terminal) {

        TerminalDto exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist != null) {
            System.out.println("Terminal code exists");
            return;
        }

        terminal.setCreatedDate(LocalDateTime.now());
        terminal.setStatusTerminal(Status.ACTIVE);
        terminal.setVisible(true);

        if (terminalRepository.adminCreateTerminal(terminal,true) > 0) {
            System.out.println("terminal successfully created");
        }

    }

    public void getTerminalList() {

        List<TerminalDto> terminalList = terminalRepository.getAllTerminal();
        if (terminalList.isEmpty()) {
            System.out.println("terminal list is empty");
        }
        for (TerminalDto terminal : terminalList) {
            System.out.println(terminal);
        }

    }
}
