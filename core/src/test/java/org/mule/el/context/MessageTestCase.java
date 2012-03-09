/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.el.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.transformer.types.SimpleDataType;

import org.junit.Test;
import org.mockito.Mockito;

public class MessageTestCase extends AbstractELTestCase
{
    public MessageTestCase(Variant variant)
    {
        super(variant);
    }

    @Test
    public void message() throws Exception
    {
        MuleMessage message = new DefaultMuleMessage("foo", muleContext);
        assertTrue(evaluate("message", message) instanceof MessageContext);
        assertEquals("foo", evaluate("message.payload", message));
    }

    @Test
    public void assignToMessage() throws Exception
    {
        MuleMessage message = new DefaultMuleMessage("", muleContext);
        assertImmutableVariable("message='foo'", message);
    }

    @Test
    public void messageId() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getUniqueId()).thenReturn("1");
        assertEquals("1", evaluate("message.id", message));
        assertFinalProperty("message.id=2", message);
    }

    @Test
    public void rootId() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getMessageRootId()).thenReturn("2");
        assertEquals("2", evaluate("message.rootId", message));
        assertFinalProperty("message.rootId=2", message);
    }

    @Test
    public void correlationId() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getCorrelationId()).thenReturn("3");
        assertEquals("3", evaluate("message.correlationId", message));
        assertFinalProperty("message.correlationId=2", message);
    }

    @Test
    public void correlationSequence() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getCorrelationSequence()).thenReturn(4);
        assertEquals(4, evaluate("message.correlationSequence", message));
        assertFinalProperty("message.correlationSequence=2", message);
    }

    @Test
    public void correlationGroupSize() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getCorrelationGroupSize()).thenReturn(4);
        assertEquals(4, evaluate("message.correlationGroupSize", message));
        assertFinalProperty("message.correlationGroupSize=2", message);
    }

    @Test
    public void replyTo() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getReplyTo()).thenReturn("replyQueue");
        assertEquals("replyQueue", evaluate("message.replyTo", message));
        assertFinalProperty("message.correlationGroupSize=2", message);
    }

    @Test
    public void assignValueToReplyTo() throws Exception
    {
        MuleMessage message = new DefaultMuleMessage("", muleContext);
        evaluate("message.replyTo='my://uri'", message);
        assertEquals("my://uri", message.getReplyTo());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void encoding() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getDataType()).thenReturn(
            new SimpleDataType(String.class, "text/xml;charset=UTF-8 "));
        assertEquals("UTF-8", evaluate("message.encoding", message));
        assertFinalProperty("message.encoding=2", message);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void mimeType() throws Exception
    {
        MuleMessage message = Mockito.mock(MuleMessage.class);
        Mockito.when(message.getDataType()).thenReturn(new SimpleDataType(String.class));
        assertEquals("*/*", evaluate("message.mimeType", message));
        assertFinalProperty("message.mimType=2", message);
    }

    @Test
    public void payload() throws Exception
    {
        MuleMessage mockMessage = Mockito.mock(MuleMessage.class);
        Object payload = new Object();
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        assertSame(payload, evaluate("message.payload", mockMessage));
    }

    @Test
    public void assignPayload() throws Exception
    {
        MuleMessage message = new DefaultMuleMessage("", muleContext);
        evaluate("message.payload = 'foo'", message);
        assertEquals("foo", message.getPayload());
    }

}