package client_server;

import database.dao.*;
import database.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Packet receivePacket = (Packet) this.in.readObject();
                System.out.println("Received: " + receivePacket.message);
                execute(receivePacket.message);
            }
        }
        catch (IOException e) {
            System.out.println("Socket disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void execute(String message) {
        Packet packet = null;
        PersonDao personDao = new PersonDao();
        PersonEntity personEntity = new PersonEntity();

        String[] phrase = message.split(" ");
        String command = phrase[0], username = null;

        switch (command) {

            case "LOGIN": {
                username = phrase[1];
                personEntity = personDao.getByUsername(username);

                String sendToClient = "";
                if (personDao.getByUsername(username) != null) {
                    sendToClient += "OKLOGIN " + username + " " + personEntity.getId() + " " + personEntity.getType();

                    if (personEntity.getType().equals("competitor")) {
                        CompetitorDao competitorDao = new CompetitorDao();
                        CompetitorEntity competitorEntity = competitorDao.getByIdPerson(personEntity.getId());
                        int idCompetitor = competitorEntity.getId();

                        StageDao stageDao = new StageDao();
                        int idStage = stageDao.getLastId();

                        ParticipationDao participationDao = new ParticipationDao();
                        ParticipationEntity participationEntity = participationDao.getById(idCompetitor, idStage);
                        if (participationEntity != null) {
                            if (participationEntity.getScore() == 0) {
                                sendToClient += "\n";
                                sendToClient += "You should register your score in the current stage !";
                            }
                        }
                    }
                    packet = new Packet(sendToClient);

                } else {
                    packet = new Packet("There is no username called " + username + " !");
                }
                break;
            }

            case "ADDSCORE": {
                username = phrase[1];
                personEntity = personDao.getByUsername(username);
                CompetitorDao competitorDao = new CompetitorDao();
                CompetitorEntity competitorEntity = competitorDao.getByIdPerson(personEntity.getId());

                StageDao stageDao = new StageDao();
                StageEntity stageEntity = stageDao.getByName(phrase[2]);

                int idCompetitor = competitorEntity.getId();
                int idStage = stageEntity.getId();
                int score = Integer.valueOf(phrase[3]);

                ParticipationDao participationDao = new ParticipationDao();
                participationDao.updateScore(idCompetitor, idStage, score);

                if (participationDao.getByScore(0) == null)
                    stageDao.updateCompleted(idStage, true);

                packet = new Packet("You have successfully added your score !");
                break;
            }

            case "STAGESCORE": {
                StageDao stageDao = new StageDao();
                StageEntity stageEntity = stageDao.getByName(phrase[1]);
                int idStage = stageEntity.getId();

                CompetitorDao competitorDao = new CompetitorDao();
                List<CompetitorEntity> allCompetitors = competitorDao.getAll();

                String sendToClient = "";
                for(CompetitorEntity competitor : allCompetitors)
                {
                    ParticipationDao participationDao = new ParticipationDao();
                    ParticipationEntity participationEntity = participationDao.getById(competitor.getId(), idStage);

                    if (participationEntity != null) {
                        personEntity = personDao.getById(competitor.getIdPerson());
                        sendToClient += personEntity.getUsername() + " " + String.valueOf(participationEntity.getScore()) + "\n";
                    }
                }

                packet = new Packet(sendToClient);
                break;
            }

            case "TEAMSCORE": {
                TeamDao teamDao = new TeamDao();
                TeamEntity teamEntity = teamDao.getByName(phrase[1]);
                int idTeam = teamEntity.getId();

                StageDao stageDao = new StageDao();
                StageEntity stageEntity = stageDao.getByName(phrase[2]);
                int idStage = stageEntity.getId();

                CompetitorDao competitorDao = new CompetitorDao();
                List<CompetitorEntity> allCompetitors = competitorDao.getCompetitorsByIdTeam(idTeam);

                String sendToClient = "";
                for(CompetitorEntity competitor : allCompetitors) {
                    ParticipationDao participationDao = new ParticipationDao();
                    ParticipationEntity participationEntity = participationDao.getById(competitor.getId(), idStage);

                    personEntity = personDao.getById(competitor.getIdPerson());

                    if (participationEntity != null) {
                        sendToClient += personEntity.getUsername() + " " + participationEntity.getScore() + "\n";
                    }
                }

                packet = new Packet(sendToClient);
                break;
            }

            case "FINALRANK": {

                break;
            }

            case "COMPETITORSCORE": {
                username = phrase[1];

                personEntity = personDao.getByUsername(username);
                int idPerson = personEntity.getId();

                CompetitorDao competitorDao = new CompetitorDao();
                CompetitorEntity competitorEntity = competitorDao.getByIdPerson(idPerson);
                int idCompetitor = competitorEntity.getId();

                StageDao stageDao = new StageDao();
                StageEntity stageEntity = stageDao.getByName(phrase[2]);

                int idStage = stageEntity.getId();

                ParticipationDao participationDao = new ParticipationDao();
                ParticipationEntity participationEntity = participationDao.getById(idCompetitor, idStage);

                packet = new Packet(username + " score: " + participationEntity.getScore());
                break;
            }

            case "ADDCOMPETITOR": {
                username = phrase[1];

                String fullname = phrase[2];
                for (int i = 3; i < phrase.length - 1; ++i)
                    fullname += " " + phrase[i];

                int idTeam = Integer.valueOf(phrase[phrase.length - 1]);

                personEntity.setUsername(username);
                personEntity.setFullname(fullname);
                personEntity.setType("competitor");

                personDao.add(personEntity);

                CompetitorDao competitorDao = new CompetitorDao();
                CompetitorEntity competitorEntity = new CompetitorEntity();
                competitorEntity.setIdPerson(personEntity.getId());
                competitorEntity.setIdTeam(idTeam);

                competitorDao.add(competitorEntity);

                packet = new Packet("You have successfully added a competitor !");
                break;
            }

            case "ADDTEAM": {
                TeamDao teamDao = new TeamDao();
                TeamEntity teamEntity = new TeamEntity();

                String teamName = phrase[1];
                for (int i = 2; i < phrase.length; ++i)
                    teamName += " " + phrase[i];

                teamEntity.setName(teamName);
                teamDao.add(teamEntity);

                packet = new Packet("You have successfully added a team !");
                break;
            }

            case "ADDSTAGE": {
                StageDao stageDao = new StageDao();
                int lastIdStage = stageDao.getLastId();

                StageEntity stageEntity = stageDao.getById(lastIdStage);
                if (!stageEntity.getCompleted()) {
                    packet = new Packet("The current stage isn't over yet. You can't add a new stage !");
                } else {
                    StageEntity stageEntity1 = new StageEntity();
                    stageEntity1.setName(phrase[1]);
                    stageDao.add(stageEntity1);
                    packet = new Packet("You have successfully added a new stage !");
                }

                break;
            }

            case "ENROLL": {
                username = phrase[1];
                personEntity = personDao.getByUsername(username);
                int idPerson = personEntity.getId();

                TeamDao teamDao = new TeamDao();
                TeamEntity teamEntity = teamDao.getByName(phrase[2]);
                int idTeam = teamEntity.getId();

                CompetitorDao competitorDao = new CompetitorDao();
                CompetitorEntity competitorEntity = competitorDao.getByIdPerson(idPerson);
                int idCompetitor = competitorEntity.getId();
                competitorDao.updateTeamId(idCompetitor, idTeam);

                packet = new Packet("You have successfully enrolled " + username + " to a team !");
                break;
            }

            case "EXIT": {
                packet = new Packet("EXIT");
                break;
            }

            default:
                packet = new Packet("Wrong command !");
        }

        try {
            this.out.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
