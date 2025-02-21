<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" />
    <xsl:strip-space elements="test-method/@name message" />

    <xsl:template match="/testng-results">
        Passed:<xsl:value-of select="@passed"/>    Failed:<xsl:value-of select="@failed"/>    Skipped:<xsl:value-of select="@skipped"/> <xsl:text>&#xa;</xsl:text><xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="suite/test/class/test-method">
            <xsl:if test="@status='FAIL' and not(contains(@signature, 'afterMethod'))">
                <xsl:value-of select="normalize-space(@name)"/>: <xsl:value-of select="normalize-space(exception/message)"/>
                <xsl:text>&#xa;</xsl:text> <xsl:text>&#xa;</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
