# GED
## Install
### Wildfly

```
<security-domain name="secureDomain" cache-type="default">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/ExampleDS"/>
            <module-option name="principalsQuery" value="SELECT password FROM User WHERE email=?"/>
            <module-option name="rolesQuery" value="SELECT Role.name, 'Roles' FROM Role INNER JOIN User ON (Role.id = User.roleId) WHERE User.email=?"/>
            <module-option name="hashAlgorithm" value="SHA-256"/>
            <module-option name="hashEncoding" value="base64"/>
        </login-module>
    </authentication>
</security-domain>
```