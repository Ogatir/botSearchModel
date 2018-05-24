public class UnifiedCriteria {

    private boolean hasFoto;
    private boolean isVerified;
    private boolean wallNotEmpty;
    private boolean highPostFreq;
    private boolean littleLikes;
    private boolean littleSources;
    private boolean lotsOfLinks;


    public UnifiedCriteria(){

    }

    public int CalculateCriteria(){
        return (hasFoto?1:0)+(isVerified?1:0)+(highPostFreq?1:0)+(littleLikes?1:0);
    }

    public void SetCriterias(boolean[] criteriaArray){
        hasFoto=criteriaArray[0];
        isVerified=criteriaArray[1];
        highPostFreq=criteriaArray[2];
        littleLikes=criteriaArray[3];
        littleSources=criteriaArray[4];
        lotsOfLinks=criteriaArray[5];
    }
}
