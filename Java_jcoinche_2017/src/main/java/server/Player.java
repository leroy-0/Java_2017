package server;

import org.apache.mina.core.session.IoSession;

import java.util.Vector;

public class Player
{
    private IoSession       session;
    private Vector<ACard>   cards = new Vector<>();
    private boolean         coinche = false;
    private ACard           bet = null;
    private Contract        contract = new Contract(-1, "");
    private int             id;

    Player(IoSession session, int id)
    {
        this.session = session;
        this.id = id;
    }

    public void             setCoinche(boolean value) {
        this.coinche = false;
    }

    public boolean          getCoinche() { return (coinche); }

    public Contract         getContract()
    {
        return (contract);
    }

    public Vector<ACard>    getCards()
    {
        return (this.cards);
    }

    public ACard            getBet()
    {
        return (bet);
    }

    public int              getId()
    {
        return (id);
    }

    public IoSession        getSession()
    {
        return (this.session);
    }

    public void             sendMsg(String msg)
    {
        this.session.write(msg);
    }

    public void             setCard(ACard card)
    {
        cards.addElement(card);
    }

    public void             removeCards()
    {
        cards.clear();
    }

    public void             setContract(String to_bid)
    {
        to_bid = to_bid.substring("Contract:".length(), to_bid.length());
        if (to_bid.substring(0, 1).equals("0"))
            this.contract = new Contract(0, "");
        else {
            this.contract = new Contract(Integer.parseInt(to_bid.substring(0, to_bid.indexOf(':'))),
                    to_bid.substring(to_bid.indexOf(':') + 1, to_bid.length()));
        }
    }

    public void             setContractCards(String type)
    {
        for (int i = 0; i < cards.size(); i++)
        {
            if (cards.elementAt(i).getClass().getSimpleName().equals(type))
            {
                cards.elementAt(i).setAsset(true);
            }
        }
    }

    public void             setBet(String to_bet)
    {
        String              type;
        String              name;

        to_bet = to_bet.substring("Bet:".length(), to_bet.length());
        type = to_bet.substring(0, to_bet.indexOf(':'));
        name = to_bet.substring(to_bet.indexOf(':') + 1, to_bet.length());
        if (type.equals("Coinche")) {
            this.coinche = true;
            return ;
        }
        for (int i = 0; i < cards.size(); i++)
        {
            if (cards.elementAt(i).getClass().getSimpleName().equals(type) && cards.elementAt(i).getName().equals(name)) {
                this.bet = cards.elementAt(i);
                cards.remove(i);
                return ;
            }
        }
        this.bet = null;
    }

    public void             removeBet()
    {
        this.bet = null;
    }
}
