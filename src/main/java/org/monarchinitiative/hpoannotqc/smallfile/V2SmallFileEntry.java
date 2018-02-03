package org.monarchinitiative.hpoannotqc.smallfile;

import com.github.phenomics.ontolib.ontology.data.TermId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by peter on 1/20/2018.
 */
public class V2SmallFileEntry {
    private static Logger logger = LoggerFactory.getLogger(V2SmallFileEntry.class);
    /** Field #1 */
    private final String diseaseID;
    /** Field #2 */
    private final String diseaseName;
    /** Field #3 */
    private final TermId phenotypeId;
    /** Field #4 */
    private final String phenotypeName;
    /** Field #5 */
    private final TermId ageOfOnsetId;
    /** Field #6 */
    private final String ageOfOnsetName;
    /** Field #7 */
    private final String evidenceCode;
    /** Field #8 */
    private final TermId frequencyId;
    /** Field #9 */
    private final String frequencyString;
    /** Field #10 */
    private final String sex;
    /** Field #11 */
    private final String negation;
    /** Field #12 */
    private final String modifier;
    /** Field #13 */
    private final String description;
    /** Field #14 */
    private final String publication;
    /** Field #15 */
    private final String assignedBy;
    /** Field #16 */
    private final String dateCreated;



    public V2SmallFileEntry(OldSmallFileEntry oldEntry) {
        diseaseID=oldEntry.getDiseaseID();
        diseaseName=oldEntry.getDiseaseName();
        phenotypeId=oldEntry.getPhenotypeId();
        phenotypeName=oldEntry.getPhenotypeName();
        ageOfOnsetId=oldEntry.getAgeOfOnsetId();
        ageOfOnsetName=oldEntry.getAgeOfOnsetName();
        String evi=oldEntry.getEvidenceID();
        if (evi==null) {
            evi=oldEntry.getEvidenceName();
        }
        if (evi==null) {
            evi=oldEntry.getEvidence();
        }
        if (evi==null) {
           logger.error("Could not get valid evidence code");
           evidenceCode="UNKKNOWN";
        } else
            evidenceCode=evi;
        frequencyId=oldEntry.getFrequencyId();
        frequencyString=oldEntry.getFrequencyString();
        sex=oldEntry.getSex();
        negation=oldEntry.getNegation();
        Set<TermId> modifierSet=oldEntry.getModifierSet();
        if (modifierSet==null || modifierSet.isEmpty()) {
            modifier="";
        } else {
            modifier=modifierSet.stream().map(TermId::getIdWithPrefix).collect(Collectors.joining(";")); }
        description=oldEntry.getDescription();
        publication=oldEntry.getPub();
        assignedBy=oldEntry.getAssignedBy();
        dateCreated=oldEntry.getDateCreated();
        System.out.println(getRow());
    }


    /**
     * @return Header line for the new V2 small files.
     */
    public static String getHeader() {
        String []fields={"#DiseaseID",
                "DiseaseName",
                "HpoId",
                "HpoName",
                "ageOfOnsetId",
                "ageOfOnsetName",
                "evidenceCode",
                "frequencyId",
                "frequencyString",
                "sex",
                "negation",
                "modifier",
                "description",
                "publication",
                "assignedBy",
                "dateCreated"};
        return Arrays.stream(fields).collect(Collectors.joining("\t"));
    }


    public String getRow() {
        return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                diseaseID,
                diseaseName,
                phenotypeId.getIdWithPrefix(),
                phenotypeName,
                ageOfOnsetId!=null?ageOfOnsetId.getIdWithPrefix() : "",
                ageOfOnsetName!=null?ageOfOnsetName:"",
                evidenceCode,
                frequencyId!=null?frequencyId.getIdWithPrefix():"",
                frequencyString!=null?frequencyString:"",
                sex,
                negation,
                modifier,
                description,
                publication,
                assignedBy,
                dateCreated);
    }


}