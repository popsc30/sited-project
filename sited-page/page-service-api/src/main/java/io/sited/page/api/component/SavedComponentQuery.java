package io.sited.page.api.component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SavedComponentQuery {
    @XmlElement(name = "query")
    public String query;
    @XmlElement(name = "status")
    public SavedComponentStatus status;
    @XmlElement(name = "page")
    public Integer page;
    @XmlElement(name = "limit")
    public Integer limit;
}
