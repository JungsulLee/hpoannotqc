package org.monarchinitiative.hpoannotqc.smallfile;

import com.github.phenomics.ontolib.formats.hpo.HpoOntology;
import com.github.phenomics.ontolib.formats.hpo.HpoTerm;
import com.github.phenomics.ontolib.formats.hpo.HpoTermRelation;
import com.github.phenomics.ontolib.ontology.data.Ontology;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static org.monarchinitiative.hpoannotqc.smallfile.DateUtil.convertToCanonicalDateFormat;

public class DateConversionTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    /*
     private HpoOntology ontology=null;
        private Ontology<HpoTerm, HpoTermRelation> inheritanceSubontology=null;
        private Ontology<HpoTerm, HpoTermRelation> abnormalPhenoSubOntology=null;
     */
    @BeforeClass
    public static void init() {
        //ClassLoader classLoader = OldSmallFileEntryTest.class.getClassLoader();
        //String hpOboPath = classLoader.getResource("hp.obo").getFile();
        //File file = ResourceUtils.getFile(OldSmallFileEntryTest.getResource("/some_file.txt"));
        Path resourceDirectory = Paths.get("src","test","resources","hp.obo");
        String hpOboPath=resourceDirectory.toAbsolutePath().toString();
        try {
            HpoOntologyParser parser = new HpoOntologyParser(hpOboPath);
            HpoOntology ontology = parser.getOntology();
            Ontology<HpoTerm, HpoTermRelation> inheritanceSubontology = parser.getInheritanceSubontology();
            Ontology<HpoTerm, HpoTermRelation> abnormalPhenoSubOntology = parser.getPhenotypeSubontology();
            OldSmallFileEntry.setOntology(ontology, inheritanceSubontology, abnormalPhenoSubOntology);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDateCorrection1() {
        String olddate = "2012.04.11";
        String expected = "2012-04-11";
        assertEquals(expected, convertToCanonicalDateFormat(olddate));
    }

    @Test
    public void testDateCorrection3() {
        String olddate = "2009.02.17";
        String expected = "2009-02-17";
        assertEquals(expected, convertToCanonicalDateFormat(olddate));
    }


    @Test
    public void testDateCorrection4() {
        String olddate="17-Feb-2009";
        String expected="2009-02-17";
        assertEquals(expected, convertToCanonicalDateFormat(olddate));
    }



}