db.addressing.ensureIndex({"jid":1,"resource":1,"priority":-1});
db.addressing.ensureIndex({"index":-1,"jid":1,"resource":1});
db.addressing.ensureIndex({"index":-1,"jid":1,"resource":1});

db.persistent.ensureIndex({"id":1});
db.persistent.ensureIndex({"sid":1,"calss":1});
db.persistent.ensureIndex({"pid":1,"calss":1});
db.persistent.ensureIndex({"activate":1,"ack":1});
db.persistent.ensureIndex({"to":1,"class":1,"type":1,"timestamp":1});
db.persistent.ensureIndex({"to":1,"activate":1,"ack":1,"class":1,"resend":1});

db.user.ensureIndex({"NICK":1});
db.user.ensureIndex({"username":1,"blocks":1});
db.user.ensureIndex({"username":1,"activate":1});

db.muc.ensureIndex({"roles.role":1});
db.muc.ensureIndex({"roles.path":1});
db.muc.ensureIndex({"roles.nick":1});
db.muc.ensureIndex({"informations.jid":1});
db.muc.ensureIndex({"jid":1,"role.path":1});
db.muc.ensureIndex({"configs.persistent":1});
db.muc.ensureIndex({"jid":1,"affiliations.jid":1});
db.muc.ensureIndex({"jid":1,"informations.jid":1});
db.muc.ensureIndex({"affiliations.affiliation":1});
db.muc.ensureIndex({"roles.jid":1,"roles.resource":1});
db.muc.ensureIndex({"affiliations.jid":1,"affiliations.nick":1});

db.roster.ensureIndex({"slave":1,"ack":1});
db.roster.ensureIndex({"slave":1,"master":1});
db.roster.ensureIndex({"master":1,"slave":1});
db.roster.ensureIndex({"slave":1,"activate":1});
db.roster.ensureIndex({"master":1,"activate":1});
db.roster.ensureIndex({"slave":1,"activate":1,"status":1});
db.roster.ensureIndex({"master":1,"activate":1,"status":1});
