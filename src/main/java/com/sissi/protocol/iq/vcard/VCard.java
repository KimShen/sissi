package com.sissi.protocol.iq.vcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.vcard.field.Binval;
import com.sissi.protocol.iq.vcard.field.Photo;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.Field.Fields;
import com.sissi.ucenter.impl.StringField;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "vCard")
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement(name = "vCard")
public class VCard extends Protocol implements Collector, Fields {

	private final static String XMLNS = "vcard-temp";

	private Photo photo;

	private String nickName;

	public VCard setPhoto(Photo photo) {
		this.photo = photo;
		return this;
	}

	@XmlElement(name = "PHOTO")
	public Photo getPhoto() {
		return photo;
	}

	public VCard setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	@XmlElement(name = "NICKNAME")
	public String getNickName() {
		return this.nickName;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.photo = (Photo) ob;
	}

	@SuppressWarnings("unchecked")
	public Fields addField(String key, Object value) {
		switch (key) {
		case "photo":
			try {
				Map<String, Object> params = (Map<String, Object>) value;
				this.setPhoto(new Photo(params.get("type").toString(), (String) params.get(Binval.class.getSimpleName().toLowerCase())));
				break;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return this;
	}

	@Override
	public List<Field> getFields() {
		List<Field> fields = new ArrayList<Field>();
		fields.add(this.getPhoto());
		fields.add(new StringField("nickname", this.getNickName()));
		return fields;
	}
}
