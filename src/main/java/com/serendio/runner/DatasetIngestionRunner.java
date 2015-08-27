package com.serendio.runner;

import com.serendio.configuration.AppConfigurations;
import com.serendio.dataset.mail.eml.EmlToNeo4j;
import com.serendio.dataset.mail.mbox.MboxToNeo4j;
import com.serendio.dataset.mail.pst.PstToNeo4j;
import org.apache.james.mime4j.MimeException;

import javax.mail.MessagingException;
import java.io.IOException;

/*
 * Created by nishant on 14/8/15.
 */
public class DatasetIngestionRunner {

    public void run(AppConfigurations conf)throws MessagingException, IOException, InterruptedException, MimeException
    {
        if(conf.getNeo4jDbDbPath() == null) {
            System.out.println("Set Neo4j Database Path");
            return;
        }
        if(conf.getINPUT_PATH() == null){
            System.out.println("Set Dataset Input Path");
            return;
        }
        if(conf.getDatasetType() == null)
        {
            System.out.println("Set Input Dataset type");
            return;
        }

        if(conf.getDatasetType()== "EML")
        {
            EmlToNeo4j eml = new EmlToNeo4j();
            eml.ingestEml(conf.getINPUT_PATH());
        }
        if(conf.getDatasetType() == "MBOX")
        {
            MboxToNeo4j mbox = new MboxToNeo4j();
            mbox.ingestMbox(conf.getINPUT_PATH());
        }
        if(conf.getDatasetType() == "PST")
        {

            PstToNeo4j pst = new PstToNeo4j();
            pst.ingestPst(conf.getINPUT_PATH());

        }
    }
}

